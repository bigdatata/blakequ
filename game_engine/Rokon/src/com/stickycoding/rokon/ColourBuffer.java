package com.stickycoding.rokon;

/**
 * ColourBuffer.java
 * Holds a set of colours, representative of each vertex
 * This is an extension of BufferObject, and behaves very similarly
 * This is used solely for glColorPointer.
 * 
 * Each vertex has 4 floats, RGBA.
 * The number of vertices here should match the number of vertices in your shape, 
 * otherwise you will see unexpected results, or trigger Exceptions.
 * 颜色缓存器(和BufferObject类似，具有四个基本的属性(x,y,width,height----R, G, B, A))
 * @author Richard
 */

public class ColourBuffer extends BufferObject {

	public ColourBuffer(float[] floats) {
		super(floats);
	}

}
