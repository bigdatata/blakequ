package com.albert.audio.sound.exception;

import com.albert.exception.GameException;


/**
 * (c) Zynga 2011
 *
 * @author Nicolas Gramlich <ngramlich@zynga.com>
 * @since 20:37:53 - 09.11.2011
 */
public class SoundException extends GameException {

	private static final long serialVersionUID = 2647561236520151571L;


	public SoundException() {
		super();
	}

	public SoundException(final String pMessage) {
		super(pMessage);
	}

	public SoundException(final Throwable pThrowable) {
		super(pThrowable);
	}

	public SoundException(final String pMessage, final Throwable pThrowable) {
		super(pMessage, pThrowable);
	}

}
