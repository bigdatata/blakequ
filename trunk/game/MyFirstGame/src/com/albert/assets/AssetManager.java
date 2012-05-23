package com.albert.assets;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.albert.assets.exception.LoadAssetException;
import com.albert.util.Disposable;

public class AssetManager implements Disposable{
	final List<AssetDescriptor> loadQueue;
	final List<AssetLoadingTask> tasksQueue;
	final ExecutorService threadPool;
	
	public AssetManager(){
		tasksQueue = new ArrayList<AssetLoadingTask>();
		loadQueue = new ArrayList<AssetDescriptor>();
		threadPool = Executors.newFixedThreadPool(1, new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable r) {
				// TODO Auto-generated method stub
				Thread thread = new Thread("AssetManager-Loader-Thread");
				//create a daemon thread
				thread.setDaemon(true);
				return thread;
			}
		});
	}

	@Override
	public synchronized void dispose() {
		// TODO Auto-generated method stub
		clear();
		threadPool.shutdown();
		try {
			threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			new LoadAssetException("Couldn't shutdown loading thread");
		}
	}
	
	public synchronized void startLoad(){
		for(AssetDescriptor ad: loadQueue){
			tasksQueue.add(new AssetLoadingTask(this, ad, threadPool));
		}
	}
	
	/**
	 * add task to task list
	 * @param assetDesc
	 */
	public synchronized void addTask(AssetDescriptor assetDesc){
		if(!containsAsset(assetDesc.fileName)){
			loadQueue.add(assetDesc);
		}
	}
	
	/**
	 * clear task list
	 */
	public synchronized void clear(){
		loadQueue.clear();
		tasksQueue.clear();
	}
	
	public synchronized boolean containsAsset(String fileName){
		for(AssetDescriptor ad: loadQueue){
			if(ad.fileName.equals(fileName)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * remove from queue
	 * @param assetDesc
	 */
	public synchronized void remove(AssetDescriptor assetDesc){
		if(!containsAsset(assetDesc.fileName)){
			return;
		}else{
			loadQueue.remove(assetDesc);
		}
	}
	
	
}
