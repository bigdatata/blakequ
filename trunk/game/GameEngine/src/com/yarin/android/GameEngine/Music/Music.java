package com.yarin.android.GameEngine.Music;

import java.util.Vector;

import android.media.MediaPlayer;

import com.yarin.android.GameEngine.GameActivity;
import com.yarin.android.GameEngine.GameObject;

//�����ࣺ����������ԴID����ԴURL�����ŷ�ʽ������ѭ������
public class Music extends GameObject
{
	//���ŷ�ʽ������ѭ��
	public static final int INFINITE_LOOP=1;
	//���ŷ�ʽ�����޴�������
	public static final int FINITE_LOOP=2;
	//��ԴURL
	private String resURL=null;
	//��������
	private String musicType=null;
	//���ŷ�ʽ
	private int playModel=0;
	//ѭ�������������޴�������ʱ��Ч
	private int loopNumber=0;
	//���ֲ�����
	private MediaPlayer musicPlayer=null;
	//��ǰ���Ŵ���
	private int currentPlayTimes=0;
	
	public Music(){
		super();
		currentPlayTimes=0;
	}
	
	public void loadProperties(Vector v){
		this.setId((String)v.elementAt(0));
		this.resURL=(String)v.elementAt(1);
		this.musicType=(String)v.elementAt(2);
		this.playModel=Integer.parseInt((String)v.elementAt(3));
		this.loopNumber=Integer.parseInt((String)v.elementAt(4));	
		try{
			musicPlayer=MediaPlayer.create(GameActivity.mContext, Integer.parseInt(resURL));
			musicPlayer.prepare();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * �Ƿ񲥷���ϣ��������޲��Ŵ����Ĳ��ŷ�ʽ��Ч
	 * @return ����ﵽ���ŵ�ѭ���������򷵻�true
	 */
	public boolean isPlayEnd(){
		if (playModel==FINITE_LOOP){
			return (currentPlayTimes>=loopNumber);
		}
		else{
			return false;
		}
	}
	
	/**
	 * ���Ӳ��Ŵ���
	 *
	 */
	public void increasePlayTimes(){
		currentPlayTimes++;
	}
	
	public int getLoopNumber() {
		return loopNumber;
	}

	public int getPlayModel() {
		return playModel;
	}

	public String getResURL() {
		return resURL;
	}

	public String getMusicType() {
		return musicType;
	}

	public MediaPlayer getMusicPlayer() {
		return musicPlayer;
	}
	
	public String toString(){
		return super.toString()
		+" resURL="+this.resURL
		+" musicType="+this.musicType
		+" playModel="+this.playModel
		+" loopNumber="+this.loopNumber;
	}
}

