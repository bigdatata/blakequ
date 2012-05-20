package com.stickycoding.rokon.device;


/**
 * AccelerometerHandler.java
 * An interface for the Accelerometer class, usual function is to be used by your Scene
 * 
 * @author Richard
 */

public interface OnAccelerometerChange {

	/**
	 * when the accelerometer changed
	 * @param x
	 * @param y
	 * @param z
	 */
	void onAccelerometerChange(float x, float y, float z);
	
	/**
	 * when shake phone
	 * @param intensity the intensity of shake
	 */
	void onShake(float intensity);

}
