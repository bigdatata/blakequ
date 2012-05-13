package com.hao.util;

import java.io.IOException;

import com.hao.MagicTower;
import com.hao.R;
import com.hao.R.raw;

import android.media.MediaPlayer;
/**
 * 音乐播放的管理类
 * 负责所有音乐的播放
 * @author Administrator
 *
 */
public class CMIDIPlayer
{
	public MediaPlayer	playerMusic;

	public MagicTower	magicTower	= null;
	//背景音乐
	public static final int MP3_MENU = 1;
	public static final int MP3_RUN = 2;


	public CMIDIPlayer(MagicTower magicTower)
	{
		this.magicTower = magicTower;

	}


	// 播放音乐
	public void PlayMusic(int ID)
	{
		FreeMusic();
		switch (ID)
		{
			case MP3_MENU:
				//装载音乐
				playerMusic = MediaPlayer.create(magicTower, R.raw.menu);
				//设置循环
				playerMusic.setLooping(true);
				try
				{
					//准备
					playerMusic.prepare();
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				//开始
				playerMusic.start();
				break;
			case MP3_RUN:
				playerMusic = MediaPlayer.create(magicTower, R.raw.run);
				playerMusic.setLooping(true);
				try
				{
					playerMusic.prepare();
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				playerMusic.start();
				break;
		}
	}


	// 退出释放资源
	public void FreeMusic()
	{
		if (playerMusic != null)
		{
			playerMusic.stop();
			playerMusic.release();
		}
	}


	// 停止播放
	public void StopMusic()
	{
		if (playerMusic != null)
		{
			playerMusic.stop();
		}
	}
}
