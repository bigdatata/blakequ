package com.albert;

import com.albert.util.Disposable;

public interface ApplicationListener extends Disposable{
	/** Called when the {@link Application} is first created. */
	public void create ();

	/** Called when the {@link Application} is resized. This can happen at any point during a non-paused state but will never happen
	 * before a call to {@link #create()}.
	 * 
	 * @param width the new width in pixels
	 * @param height the new height in pixels */
	public void resize (int width, int height);

	/** Called when the {@link Application} should render itself. */
	public void render ();

	/** Called when the {@link Application} is paused. An Application is paused before it is destroyed, when a user pressed the Home
	 * button on Android or an incoming call happend. On the desktop this will only be called immediately before {@link #dispose()}
	 * is called. */
	public void pause ();

	/** Called when the {@link Application} is resumed from a paused state. On Android this happens when the activity gets focus
	 * again. On the desktop this method will never be called. */
	public void resume ();

	/** Called when the {@link Application} is destroyed. Preceded by a call to {@link #pause()}. */
	public void dispose ();
}
