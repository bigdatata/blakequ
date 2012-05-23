package com.albert.assets;

import java.util.concurrent.ExecutorService;


public class AssetLoadingTask implements Runnable {

	AssetManager manager;
	final AssetDescriptor assetDesc;
	final ExecutorService threadPool;
	Object asset = null;
	
	public AssetLoadingTask(AssetManager manager, AssetDescriptor assetDesc, ExecutorService threadPool){
		this.manager = manager;
		this.assetDesc = assetDesc;
		this.threadPool = threadPool;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
	
	public Object getAsset(){
		return asset;
	}

}
