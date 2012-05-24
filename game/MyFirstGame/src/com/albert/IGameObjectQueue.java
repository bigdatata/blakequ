package com.albert;

import java.util.HashMap;
import java.util.Iterator;

import android.util.Log;

/**
 * using to manager the queue of game object
 * @author AlbertQu
 *
 */
public class IGameObjectQueue extends HashMap<Integer, IGameObject>{

	/**
	 * add {@link IGameObject} to queue, if exsits, not add to queue.
	 * @param id
	 * @param object
	 */
	public void add(int id, IGameObject object){
		if(this.containsKey(id)){
			Log.w("IGamethis", object.getName()+" has exsits in object queue");
		}else{
			this.put(id, object);
		}
	}
	
	/**
	 * if exsits in queue, will update the object
	 * @param id
	 * @param object
	 */
	public void addAndUpdate(int id, IGameObject object){
		this.put(id, object);
	}

	public IGameObject get(int id){
		return this.get(id);
	}
	
	public void remove(int id){
		this.remove(id);
	}
	
	public IGameObject[] list(){
		IGameObject[] objs = new IGameObject[this.size()]; 
		Iterator itr = this.keySet().iterator();
		int i=0;
		while(itr.hasNext()){
			IGameObject igo = this.get(itr.next());
			objs[i++] = igo;
		}
		return objs;
	}
	
	/**
	 * garbage the dead game object
	 */
	public void gc(){
		Iterator itr = this.keySet().iterator();
		while(itr.hasNext()){
			IGameObject igo = this.get(itr.next());
			if(!igo.isAlive()){
				igo = null;
				itr.remove();
			}
		}
	}
	
	public void clear(){
		this.clear();
	}
}
