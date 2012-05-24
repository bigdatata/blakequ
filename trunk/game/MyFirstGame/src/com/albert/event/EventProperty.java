package com.albert.event;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import android.content.Context;

import com.albert.IProperty;
import com.albert.audio.music.exception.MusicLoadPropertyException;

public class EventProperty implements IProperty {

	public int id;
	public String invoker=null;
	public String responser=null;
	public int type=0;
	public String parameter=null;
	
	private String file;
	private Context mContext;
	
	
	public EventProperty(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public void readProperty() {
		// TODO Auto-generated method stub
		Properties properties = new Properties();
		try{
			FileInputStream stream = mContext.openFileInput(file);
			properties.load(stream);
			this.id = Integer.parseInt(properties.get("id").toString());
			this.invoker = properties.get("invoker").toString();
			this.responser = properties.get("responser").toString();
			this.type = Integer.parseInt(properties.get("type").toString());
			this.parameter = properties.get("parameter").toString();
			
		}catch (FileNotFoundException e)
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
		p.put("invoker", invoker);
		p.put("responser", responser);
		p.put("type", type);
		p.put("parameter", parameter);
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
