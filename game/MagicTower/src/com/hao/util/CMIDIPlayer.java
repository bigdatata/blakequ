package com.hao.util;

import java.io.IOException;

import com.hao.MagicTower;
import com.hao.R;
import com.hao.R.raw;

import android.media.MediaPlayer;
/**
 * ���ֲ��ŵĹ�����
 * �����������ֵĲ���
 * @author Administrator
 *
 */
public class CMIDIPlayer
{
	public MediaPlayer	playerMusic;

	public MagicTower	magicTower	= null;
	//��������
	public static final int MP3_MENU = 1;
	public static final int MP3_RUN = 2;


	public CMIDIPlayer(MagicTower magicTower)
	{
		this.magicTower = magicTower;

	}


	// ��������
	public void PlayMusic(int ID)
	{
		FreeMusic();
		switch (ID)
		{
			case MP3_MENU:
				//װ������
				playerMusic = MediaPlayer.create(magicTower, R.raw.menu);
				//����ѭ��
				playerMusic.setLooping(true);
				try
				{
					//׼��
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
				//��ʼ
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


	// �˳��ͷ���Դ
	public void FreeMusic()
	{
		if (playerMusic != null)
		{
			playerMusic.stop();
			playerMusic.release();
		}
	}


	// ֹͣ����
	public void StopMusic()
	{
		if (playerMusic != null)
		{
			playerMusic.stop();
		}
	}
}
