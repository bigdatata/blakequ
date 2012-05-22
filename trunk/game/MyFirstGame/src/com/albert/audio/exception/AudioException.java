package com.albert.audio.exception;


import com.albert.exception.GameException;

/**
 * 
 * @author AlbertQu
 *
 */
public class AudioException extends GameException {

	private static final long serialVersionUID = 2647561236520151571L;

	public AudioException() {
		super();
	}

	public AudioException(final String pMessage) {
		super(pMessage);
	}

	public AudioException(final Throwable pThrowable) {
		super(pThrowable);
	}

	public AudioException(final String pMessage, final Throwable pThrowable) {
		super(pMessage, pThrowable);
	}

}
