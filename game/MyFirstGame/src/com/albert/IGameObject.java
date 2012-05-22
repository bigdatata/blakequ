package com.albert;

/**
 * 
 * @author AlbertQu
 *
 */
public abstract class IGameObject implements GameObjectInterface {
	
	private int id;
	//the state of current object, if dead, set false
	private boolean alive = true;
	@Override
	public void loadProperities(IProperty property) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setId(int id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
	
	/**
	 * get the name of class
	 */
	public String getName(){
		return this.getClass().getName();
	}
	
	public int getId(){
		return id;
	}

	/**
	 * check the object state
	 * @return
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * get the state of current object
	 * @param alive
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
}
