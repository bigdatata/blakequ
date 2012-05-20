package com.stickycoding.rokon;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.os.Build;

/**
 * BufferObject.java
 * Some functions for Buffers
 * �����һ���洢�������Ե��ࣨ����x,y,width,height��
 * @author Richard 
 */

public class BufferObject {

	protected ByteBuffer byteBuffer;
	/**
	 * the number of points
	 */
	protected int size;
	
	/**
	 * Returns the size of the buffer, ie number of points
	 * 
	 * @return integer buffer size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Creates a BufferObject, with a specific length and empty values 
	 * 
	 * @param length a valid integer, number of points
	 */
	public BufferObject(int length) {
		if(Build.VERSION.SDK == "3")
			/**
			 * ����ʱ��length*4��Ϊһ�����ε�ȷ����Ҫһ����(x,y)�ͳ���(length,width)���ĸ�����
			 */
			byteBuffer = ByteBuffer.allocate(length*4);
		else
			byteBuffer = ByteBuffer.allocateDirect(length*4);
		byteBuffer.order(ByteOrder.nativeOrder());	
		size = length;
	}
	
	/**
	 * Creates a BufferObject from a given float array
	 * 
	 * @param floats an array of points for the buffer
	 */
	public BufferObject(float[] floats) {
		if(Build.VERSION.SDK == "3")
			byteBuffer = ByteBuffer.allocate(floats.length * 4);
		else
			byteBuffer = ByteBuffer.allocateDirect(floats.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		updateRaw(floats);
		size = floats.length;
	}
	
	/**
	 * Removes the buffer from memory, good practice to use this
	 */
	public void free() {
		byteBuffer.clear();
		byteBuffer = null;
	}
	
	/**
	 * Updates the BufferObject with a new set of points, passed by a float array.
	 * The length of this array must match the size of the BufferObject as it stands.
	 * Unexpected results (or Exceptions) will arise if not used properly.
	 * ��float����(������)��ʼ��������
	 * @param floats an array of points for the buffer
	 */
	public void updateRaw(float[] floats) {
		byteBuffer.position(0);
		for(int i = 0; i < floats.length; i++) {
			byteBuffer.putFloat(floats[i]);
		}
		byteBuffer.position(0);
	}
	
	/**
	 * Updates the BufferObject with a new set of points, assuming rectangular shape.
	 * This should only be used in a BufferObject of length 8. Otherwise errors may occur.
	 * Merely for convenience, it is wiser to use updateRaw
	 * ���涨����һ�����ε��ĸ�������
	 * @param x x-coordinate 
	 * @param y y-coordinate 
	 * @param width width of rectangle
	 * @param height height of rectangle
	 */
	public void update(float x, float y, float width, float height) {
		byteBuffer.position(0);
		//left-up point
		byteBuffer.putFloat(x);
		byteBuffer.putFloat(y);
		//right-up point
		byteBuffer.putFloat(x + width);
		byteBuffer.putFloat(y);
		//left-down point
		byteBuffer.putFloat(x);
		byteBuffer.putFloat(y + height);
		//right-down point
		byteBuffer.putFloat(x + width);
		byteBuffer.putFloat(y + height);
		byteBuffer.position(0);
	}
	
	/**
	 * Returns the ByteBuffer object created by BufferObject
	 * 
	 * @return ByteBuffer object, NULL if not valid
	 */
	public ByteBuffer get() {
		return byteBuffer;
	}
	
}
