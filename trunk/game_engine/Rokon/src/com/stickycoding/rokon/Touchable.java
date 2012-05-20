package com.stickycoding.rokon;

import android.view.MotionEvent;

/**
 * Touchable.java
 * An interface applied to objects to allow responses to screen touch events
 * �Ի�����Ϸ����Ĵ��������ӿ�
 * @author Richard
 *
 */
public interface Touchable {
	
	void onTouchDown(BasicGameObject object, int x, int y, MotionEvent event);
	void onTouch(BasicGameObject object, int x, int y, MotionEvent event);
	void onTouchUp(BasicGameObject object, int x, int y, MotionEvent event);

}
