package com.albert;

import java.util.HashMap;
import java.util.Iterator;

import android.util.Log;

/**
 * using to manager the queue of game object
 * @author AlbertQu
 *
 */
public class IGameObjectQueue{
	private static IGameObjectQueue instance = null;
	private static HashMap<Integer, IGameObject> objectQueue = new HashMap<Integer, IGameObject>();
	private IGameObjectQueue(){}
	
	public synchronized static IGameObjectQueue getInstance(){
		if(instance == null){
			instance = new IGameObjectQueue();
		}
		return instance;
	}

	/**
	 * add {@link IGameObject} to queue, if exsits, not add to queue.
	 * @param id
	 * @param object
	 */
	public void add(int id, IGameObject object){
		if(objectQueue.containsKey(id)){
			Log.w("IGameObjectQueue", object.getName()+" has exsits in object queue");
		}else{
			objectQueue.put(id, object);
		}
	}
	
	/**
	 * if exsits in queue, will update the object
	 * @param id
	 * @param object
	 */
	public void addAndUpdate(int id, IGameObject object){
		objectQueue.put(id, object);
	}

	public IGameObject get(int id){
		return objectQueue.get(id);
	}
	
	public void remove(int id){
		objectQueue.remove(id);
	}
	
	/**
	 * garbage the dead game object
	 */
	public void gc(){
		Iterator itr = objectQueue.keySet().iterator();
		while(itr.hasNext()){
			IGameObject igo = objectQueue.get(itr.next());
			if(!igo.isAlive()){
				itr.remove();
			}
		}
	}
	
	public void clear(){
		objectQueue.clear();
	}
}
