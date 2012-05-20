package com.stickycoding.rokon;

import android.view.MotionEvent;

/**
 * API_LEVEL=5�Ĵ����¼���װ��
 * @author AlbertQu
 *
 */
public class MotionEventWrapper5 {

	//��������㣬ÿ���Ķ���(UP and DOWN)
	protected static final int ACTION_POINTER_1_DOWN = 0x00000005;
	protected static final int ACTION_POINTER_1_UP = 0x00000006;
	protected static final int ACTION_POINTER_2_DOWN = 0x00000105;
	protected static final int ACTION_POINTER_2_UP = 0x00000106;
	protected static final int ACTION_POINTER_3_DOWN = 0x00000205;
	protected static final int ACTION_POINTER_3_UP = 0x00000206;
	
	protected MotionEventWrapper5() {
		try {
			MotionEvent.class.getMethod("getPointerCount", new Class[] { });
		} catch (Exception e) {
			Debug.print("MOTIONEVENT 5 NOT FOUND");
			e.printStackTrace();
			//throw new RuntimeException(e);
		}
	}
	
	protected static void checkAvailable() { }
	
	/**
	 * check the number of point
	 * @param motionEvent
	 * @return
	 */
	protected int getPointerCount(MotionEvent motionEvent) {
		return motionEvent.getPointerCount();
	}
	
	/**
	 * get the id of index MotionEvent
	 * @param motionEvent
	 * @param index
	 * @return
	 */
	protected int getPointerId(MotionEvent motionEvent, int index) {
		return motionEvent.getPointerId(index);
	}
	
	/**
	 * get x of index MotionEvent
	 * @param motionEvent
	 * @param index
	 * @return
	 */
	protected float getX(MotionEvent motionEvent, int index) {
		return motionEvent.getX(index);
	}
	
	/**
	 * get y of index MotionEvent
	 * @param motionEvent
	 * @param index
	 * @return
	 */
	protected float getY(MotionEvent motionEvent, int index) {
		return motionEvent.getY(index);
	}



}
