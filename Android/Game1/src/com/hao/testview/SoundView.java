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
 * ��������SoundPool,MediaPlayer��ʹ��
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
    private HashMap<Integer, Integer> soundPoolMap;//��ע1  
    private int loadId;  
    private SoundPool soundPool;  
	public SoundView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		// ��ȡ��Ƶ����Ȼ��ǿת��һ����Ƶ������,���淽����������������С��  
        am = (AudioManager) Game1Activity.instance.getSystemService(Context.AUDIO_SERVICE);  
        maxVol = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);  
        // ��ȡ�������ֵ��15���! .����100����  
        sfh = this.getHolder();  
        sfh.addCallback(this);  
        th = new Thread(this);  
        this.setKeepScreenOn(true);  
        setFocusable(true);  
        paint = new Paint();  
        paint.setAntiAlias(true);  
        
        //---------------MediaPlayer�ĳ�ʼ��  
        player = MediaPlayer.create(context, R.raw.himi);   
//        player.setLooping(true);//����ѭ������  
        
        //----------------SoundPool�ĳ�ʼ��
        //SoundPool���ֻ������1M���ڴ�ռ䣬�����ζ������ֻ��ʹ��һЩ�̵ܶ�����Ƭ��
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);  
        soundPoolMap = new HashMap<Integer, Integer>();  
        soundPoolMap.put(1, soundPool.load(Game1Activity.content,  
                R.raw.himi_ogg, 1));  
        loadId = soundPool.load(context, R.raw.himi_ogg, 1);  
        //load()���������һ����������ʶ���ȿ��ǵ�������Ŀǰû���κ�Ч����ʹ����Ҳֻ�Ƕ�δ���ļ����Լ�ֵ��
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		/*  
         * Android OS�У������ȥ���ֻ��ϵĵ��������İ�ť��������������  
         * һ���ǵ����ֻ����������������һ���ǵ�����Ϸ����������ֲ��ŵ�����  
         * ����������Ϸ�е�ʱ�� �������������Ϸ�������������ֻ�������������  
         * ���Ƿ��˵����������ˣ����ڿ����з��֣�ֻ����Ϸ���������ڲ��ŵ�ʱ��  
         * �������ȥ������Ϸ����������������ֻ�����������û�а취���ֻ�ֻҪ��  
         * ��������Ϸ��״̬��ֻ������Ϸ�������أ�����������δ����!  
         */  
		Game1Activity.instance.setVolumeControlStream(AudioManager.STREAM_MUSIC);  
        // �趨��������Ϊý������,����ͣ���ŵ�ʱ����������Ͳ�����Ĭ�ϵ������������ˣ��޹���  
        flag = true;
        if(player != null){
        	player.setOnPreparedListener(new OnPreparedListener() {
				
				@Override
				public void onPrepared(MediaPlayer mp) {
					// TODO Auto-generated method stub
					// ��ʱ��ȷ��player����Prepared״̬������start������ʵ�
//					player.start();  
					System.out.println("--------onPrepared-------");
				}
			});
        	player.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					// �������Ž��������Դ���������һ��
				}
			});
        	player.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					// TODO Auto-generated method stub]
					// �������������ԭ���µĴ���������ﱻ֪ͨ
					player.stop();
					player.release();
					return false;
				}
			});
        }
        //���Ӳ�������Դ
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
        canvas.drawText("��ǰ����: " + currentVol, 100, 40, paint);  
        canvas.drawText("��ǰ���ŵ�ʱ��" + player.getCurrentPosition() + "����", 100,  
                70, paint);  
        canvas.drawText("������м䰴ť�л� ��ͣ/��ʼ", 100, 100, paint);  
        canvas.drawText("�������������5�� ", 100, 130, paint);  
        canvas.drawText("������������5�� ", 100, 160, paint);  
        canvas.drawText("����������������� ", 100, 190, paint);  
        canvas.drawText("�����������С����", 100, 220, paint);  
        sfh.unlockCanvasAndPost(canvas);  
    }  
	
    private void logic() {  
        currentVol = am.getStreamVolume(AudioManager.STREAM_MUSIC);// ���ϻ�ȡ��ǰ������ֵ  
    }  
    
    @Override  
    public boolean onKeyDown(int key, KeyEvent event) {  
        if (key == KeyEvent.KEYCODE_DPAD_CENTER) {  
            ON = !ON;  
            if (ON == false)  
                player.pause();  
            else  
                player.start();   
        } else if (key == KeyEvent.KEYCODE_DPAD_UP) {// �������ﱾӦ����RIGHT,������Ϊ��ǰ�Ǻ���ģʽ,������ͬ  
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
            am.setStreamVolume(AudioManager.STREAM_MUSIC, currentVol,// ��ע2  
                    AudioManager.FLAG_PLAY_SOUND);  
        } else if (key == KeyEvent.KEYCODE_DPAD_RIGHT) {  
            currentVol -= 1;  
            if (currentVol <= 0) {  
                currentVol = 0;  
            }  
            am.setStreamVolume(AudioManager.STREAM_MUSIC, currentVol,  
                    AudioManager.FLAG_PLAY_SOUND);  
        }  
        //ÿ�ΰ�������������--SoundPool�Ƚ��ʺ����ֶ��ݶ�ʵʱ������
        soundPool.play(loadId, currentVol, currentVol, 1, 0, 1f);// ��ע3  
//      soundPool.play(soundPoolMap.get(1), currentVol, currentVol, 1, 0, 1f);//��ע4  
//      soundPool.pause(1);//��ͣSoundPool������   
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
		//��ֹ���Ų��ͷ���Դ
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
�����ʹ��SoundPool���ڵ�һЩ����:
1. SoundPool���ֻ������1M���ڴ�ռ䣬�����ζ������ֻ��ʹ��һЩ�̵ܶ�����Ƭ�Σ����������������Ÿ���������Ϸ�������֣��������ֿ��Կ���ʹ��JetPlayer�����ţ���
2. SoundPool�ṩ��pause��stop����������Щ����������ò�Ҫ����ʹ�ã���Ϊ��Щʱ�����ǿ��ܻ�ʹ��ĳ���Ī���������ֹ������Щ���ѷ�ӳ���ǲ���������ֹ�������������ǰѻ�����������ݲ�����Ż�ͣ������Ҳ���ಥ��һ���ӡ�
3. ��Ƶ��ʽ����ʹ��OGG��ʽ��ʹ��WAV��ʽ����Ƶ�ļ������Ϸ��Ч�������������ԣ�����Ч���ż���϶̵�����»�����쳣�رյ��������˵����SoundPoolĿǰֻ��16bit��WAV�ļ��нϺõ�֧�֣����������ļ�ת��OGG��ʽ������õ��˽����
4.��ʹ��SoundPool������Ƶ��ʱ������ڳ�ʼ���о͵��ò��ź������в���������ô����û��������������Ϊû��ִ�У�����SoundPool��Ҫһ׼��ʱ�䣡�塣��Ȼ���׼��ʱ��Ҳ�̣ܶ�����Ӱ��ʹ�ã�ֻ�ǳ���һ���оͲ��Ż�û���������ˣ������Ұ�SoundPool����д���˰����д����ˡ���ע4�ĵط�
*/