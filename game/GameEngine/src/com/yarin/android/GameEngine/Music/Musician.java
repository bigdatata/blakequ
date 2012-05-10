package com.yarin.android.GameEngine.Music;

import java.util.Enumeration;

import android.media.MediaPlayer;

import com.yarin.android.GameEngine.GameObjectQueue;
//���ּ��ࣺ����������Music����
public class Musician extends GameObjectQueue
{
	public Musician(){
		super();
	}

	/**
	 * �����ּҵ�����ȡ��ĳ�����֣���ȡ�������ֽ���Ϊ��ǰ����
	 * @param musicID ������ԴID
	 * @return ���ֶ���
	 */
	public Music takeMusicFromMusicBox(String musicID){
		if (this.containsKey(musicID)){
			return (Music)this.get(musicID);
		}
		else{
			return null;
		}
	}
	
	/**
	 * �����ּҵ�����ȡ��ĳ�����֣���ȡ�������ֽ���Ϊ��ǰ����
	 * @param player Player����
	 * @return ���ֶ���
	 */
	public Music takeMusicFromMusicBox(MediaPlayer player){
		Enumeration emu=this.elements();
		while(emu.hasMoreElements()){
			Music music=(Music)emu.nextElement();
			if (music.getMusicPlayer().equals(player)){
				return music;
			}
		}
		return null;
	}
	
	/**
	 * �����ֶ��󽻸����ּ�
	 * @param music ���ֶ���
	 */
	public void putMusicIntoMusicBox(Music music){
		this.put(music.getId(),music);
	}
}

