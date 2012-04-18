package com.hao.testview;

import java.io.IOException;
import java.util.HashMap;

import com.hao.Game1Activity;
import com.hao.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * 关于声音SoundPool,MediaPlayer的使用
 * http://dev.10086.cn/cmdn/wiki/index.php?doc-view-5522.html
 * http://xiaominghimi.blog.51cto.com/2614927/606131
 * @author Administrator
 *
 */
public class SoundView extends SurfaceView implements Callback, Runnable{

	private Thread th;  
    private SurfaceHolder sfh;  
    private Canvas canvas;  
    private MediaPlayer player;  
    private Paint paint;  
    private boolean ON = true, flag = true;  
    private int currentVol, maxVol;  
    private AudioManager am;   
    private HashMap<Integer, Integer> soundPoolMap;//备注1  
    private int loadId;  
    private SoundPool soundPool;  
	public SoundView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		// 获取音频服务然后强转成一个音频管理器,后面方便用来控制音量大小用  
        am = (AudioManager) Game1Activity.instance.getSystemService(Context.AUDIO_SERVICE);  
        maxVol = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);  
        // 获取最大音量值（15最大! .不是100！）  
        sfh = this.getHolder();  
        sfh.addCallback(this);  
        th = new Thread(this);  
        this.setKeepScreenOn(true);  
        setFocusable(true);  
        paint = new Paint();  
        paint.setAntiAlias(true);  
        
        //---------------MediaPlayer的初始化  
        player = MediaPlayer.create(context, R.raw.himi);   
//        player.setLooping(true);//设置循环播放  
        
        //----------------SoundPool的初始化
        //SoundPool最大只能申请1M的内存空间，这就意味着我们只能使用一些很短的声音片段
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);  
        soundPoolMap = new HashMap<Integer, Integer>();  
        soundPoolMap.put(1, soundPool.load(Game1Activity.content,  
                R.raw.himi_ogg, 1));  
        loadId = soundPool.load(context, R.raw.himi_ogg, 1);  
        //load()方法的最后一个参数他标识优先考虑的声音。目前没有任何效果。使用了也只是对未来的兼容性价值。
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		/*  
         * Android OS中，如果你去按手机上的调节音量的按钮，会分两种情况，  
         * 一种是调整手机本身的铃声音量，一种是调整游戏，软件，音乐播放的音量  
         * 当我们在游戏中的时候 ，总是想调整游戏的音量而不是手机的铃声音量，  
         * 可是烦人的问题又来了，我在开发中发现，只有游戏中有声音在播放的时候  
         * ，你才能去调整游戏的音量，否则就是手机的音量，有没有办法让手机只要是  
         * 在运行游戏的状态就只调整游戏的音量呢？试试下面这段代码吧!  
         */  
		Game1Activity.instance.setVolumeControlStream(AudioManager.STREAM_MUSIC);  
        // 设定调整音量为媒体音量,当暂停播放的时候调整音量就不会再默认调整铃声音量了，娃哈哈  
        flag = true;
        if(player != null){
        	player.setOnPreparedListener(new OnPreparedListener() {
				
				@Override
				public void onPrepared(MediaPlayer mp) {
					// TODO Auto-generated method stub
					// 这时能确保player处于Prepared状态，触发start是最合适的
//					player.start();  
					System.out.println("--------onPrepared-------");
				}
			});
        	player.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					// 正常播放结束，可以触发播放下一首
				}
			});
        	player.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					// TODO Auto-generated method stub]
					// 操作错误或其他原因导致的错误会在这里被通知
					player.stop();
					player.release();
					return false;
				}
			});
        }
        //连接并加载资源
        try {
			player.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		player.start();
        th.start();  
	}
	
	public void draw() {  
        canvas = sfh.lockCanvas();  
        canvas.drawColor(Color.WHITE);  
        paint.setColor(Color.RED);  
        canvas.drawText("当前音量: " + currentVol, 100, 40, paint);  
        canvas.drawText("当前播放的时间" + player.getCurrentPosition() + "毫秒", 100,  
                70, paint);  
        canvas.drawText("方向键中间按钮切换 暂停/开始", 100, 100, paint);  
        canvas.drawText("方向键←键快退5秒 ", 100, 130, paint);  
        canvas.drawText("方向键→键快进5秒 ", 100, 160, paint);  
        canvas.drawText("方向键↑键增加音量 ", 100, 190, paint);  
        canvas.drawText("方向键↓键减小音量", 100, 220, paint);  
        sfh.unlockCanvasAndPost(canvas);  
    }  
	
    private void logic() {  
        currentVol = am.getStreamVolume(AudioManager.STREAM_MUSIC);// 不断获取当前的音量值  
    }  
    
    @Override  
    public boolean onKeyDown(int key, KeyEvent event) {  
        if (key == KeyEvent.KEYCODE_DPAD_CENTER) {  
            ON = !ON;  
            if (ON == false)  
                player.pause();  
            else  
                player.start();   
        } else if (key == KeyEvent.KEYCODE_DPAD_UP) {// 按键这里本应该是RIGHT,但是因为当前是横屏模式,以下雷同  
            player.seekTo(player.getCurrentPosition() + 5000);  
        } else if (key == KeyEvent.KEYCODE_DPAD_DOWN) {  
            if (player.getCurrentPosition() < 5000) {  
                player.seekTo(0);  
            } else {  
                player.seekTo(player.getCurrentPosition() - 5000);  
            }  
        } else if (key == KeyEvent.KEYCODE_DPAD_LEFT) {  
            currentVol += 1;  
            if (currentVol > maxVol) {  
                currentVol = 100;  
            }  
            am.setStreamVolume(AudioManager.STREAM_MUSIC, currentVol,// 备注2  
                    AudioManager.FLAG_PLAY_SOUND);  
        } else if (key == KeyEvent.KEYCODE_DPAD_RIGHT) {  
            currentVol -= 1;  
            if (currentVol <= 0) {  
                currentVol = 0;  
            }  
            am.setStreamVolume(AudioManager.STREAM_MUSIC, currentVol,  
                    AudioManager.FLAG_PLAY_SOUND);  
        }  
        //每次按键都发出声音--SoundPool比较适合这种短暂而实时的声音
        soundPool.play(loadId, currentVol, currentVol, 1, 0, 1f);// 备注3  
//      soundPool.play(soundPoolMap.get(1), currentVol, currentVol, 1, 0, 1f);//备注4  
//      soundPool.pause(1);//暂停SoundPool的声音   
        return super.onKeyDown(key, event);  
    }   

    public void run() {  
        // TODO Auto-generated method stub  
        while (flag) {  
            draw();  
            logic();  
            try {  
                Thread.sleep(100);  
            } catch (Exception ex) {  
            }  
        }  
    }   
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		flag = false;
		//终止播放并释放资源
		try{
			if(player != null){
				System.out.println("-----------release-----------");
				player.stop();
				player.release();
			}
		}catch(IllegalStateException e){
			e.printStackTrace();
		}
	}


}

/**
相对于使用SoundPool存在的一些问题:
1. SoundPool最大只能申请1M的内存空间，这就意味着我们只能使用一些很短的声音片段，而不是用它来播放歌曲或者游戏背景音乐（背景音乐可以考虑使用JetPlayer来播放）。
2. SoundPool提供了pause和stop方法，但这些方法建议最好不要轻易使用，因为有些时候它们可能会使你的程序莫名其妙的终止。还有些朋友反映它们不会立即中止播放声音，而是把缓冲区里的数据播放完才会停下来，也许会多播放一秒钟。
3. 音频格式建议使用OGG格式。使用WAV格式的音频文件存放游戏音效，经过反复测试，在音效播放间隔较短的情况下会出现异常关闭的情况（有说法是SoundPool目前只对16bit的WAV文件有较好的支持）。后来将文件转成OGG格式，问题得到了解决。
4.在使用SoundPool播放音频的时候，如果在初始化中就调用播放函数进行播放音乐那么根本没有声音，不是因为没有执行，而是SoundPool需要一准备时间！囧。当然这个准备时间也很短，不会影响使用，只是程序一运行就播放会没有声音罢了，所以我把SoundPool播放写在了按键中处理了、备注4的地方
*/