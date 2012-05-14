package com.hao.otheraudio;
interface IServicePlayer{
	void play();
	void stop();
	boolean isPlaying();
	int getDuration();
	int getCurrentPosition();
	int getSecondPosition();
	void seekTo(int current);
	boolean setLoop(boolean loop);
}