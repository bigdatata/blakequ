package com.stickycoding.rokon;

/**
 * UpdateableObject.java
 * An interface for objects that need updating with the render thread / game loop
 * 对象更新监听接口
 * @author Richard
 */

public interface Updateable {
	
	void onUpdate();

}
