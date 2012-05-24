package com.albert.audio.sound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import android.content.Context;

import com.albert.IProperty;
import com.albert.audio.music.exception.MusicLoadPropertyException;

public class SoundProperty implements IProperty {

	public int id;
	public float leftVolume;
	public float rightVolume;
	public String url;
	public boolean isMute;
	public int[] soundID;
	public int[] streamID;
	public float rate;
	public int loopCount;
	
	public String file;
	public Context mContext;

	public SoundProperty(Context mContext){
		super();
		this.mContext = mContext;
	}

	@Override
	public void readProperty() {
		// TODO Auto-generated method stub
		Properties properties = new Properties();
		try
		{
			FileInputStream stream = mContext.openFileInput(file);
			properties.load(stream);
			this.id = Integer.parseInt(properties.get("id").toString());
			this.leftVolume = Float.parseFloat(properties.get("leftVolume").toString());
			this.rightVolume = Float.parseFloat(properties.get("rightVolume").toString());
			this.url = properties.get("url").toString();
			this.isMute = Boolean.parseBoolean(properties.get("isMute").toString());
			int length = Integer.parseInt(properties.get("length").toString());
			soundID = new int[length];
			streamID = new int[length];
			for(int i=0; i<length; i++){
				soundID[i] = Integer.parseInt(properties.get("soundID_"+i).toString());
				streamID[i] = Integer.parseInt(properties.get("streamID_"+i).toString());
			}
			this.rate = Float.parseFloat(properties.get("rate").toString());
			this.loopCount = Integer.parseInt(properties.get("loopCount").toString());
		}
		catch (FileNotFoundException e)
		{
			throw new MusicLoadPropertyException(e.getMessage());
		}
		catch (IOException e)
		{
			throw new MusicLoadPropertyException(e.getMessage());
		}
		catch (NumberFormatException e){
			throw new MusicLoadPropertyException(e.getMessage());
		}
	}

	@Override
	public void saveProperty() {
		// TODO Auto-generated method stub
		Properties p = new Properties();
		p.put("id", id);
		p.put("leftVolume", leftVolume);
		p.put("rightVolume", rightVolume);
		p.put("url", url);
		p.put("isMute", isMute);
		p.put("length", soundID.length);
		for(int i=0; i<soundID.length; i++){
			p.put("soundID_"+i, soundID[i]);
			p.put("streamID_", streamID[i]);
		}
		p.put("rate", rate);
		p.put("loopCount", loopCount);
		try
		{
			FileOutputStream stream = mContext.openFileOutput(file, Context.MODE_WORLD_WRITEABLE);
			p.store(stream, "");
		}
		catch (FileNotFoundException e)
		{
			throw new MusicLoadPropertyException(e.getMessage());
		}
		catch (IOException e)
		{
			throw new MusicLoadPropertyException(e.getMessage());
		}
	}


	@Override
	public void setStoreFile(String file) {
		// TODO Auto-generated method stub
		this.file = file;
	}

}
