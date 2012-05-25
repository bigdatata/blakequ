package com.albert.audio;

import com.albert.GamePara;

public class AudioSetting {
	/* is playing background audio */
	private boolean playBackgroundAudio = true;
	/* is playing action audio */
	private boolean playActionAudio = false;
	/* the type of current type*/
	private int audioType = GamePara.SOUND;
	
	public boolean isPlayBackgroundAudio() {
		return playBackgroundAudio;
	}
	public boolean isPlayActionAudio() {
		return playActionAudio;
	}
	public void setPlayBackgroundAudio(boolean playBackgroundAudio) {
		this.playBackgroundAudio = playBackgroundAudio;
	}
	public void setPlayActionAudio(boolean playActionAudio) {
		this.playActionAudio = playActionAudio;
	}
	public int getAudioType() {
		return audioType;
	}
	public void setAudioType(int audioType) {
		this.audioType = audioType;
	}
	
	/**
	 * mute
	 * @return
	 */
	public boolean isMute(){
		return !(playBackgroundAudio || playActionAudio);
	}
	
}
