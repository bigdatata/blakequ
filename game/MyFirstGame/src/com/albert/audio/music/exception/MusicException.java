package com.albert.audio.music.exception;


import com.albert.exception.GameException;

/**
 * (c) Zynga 2011
 *
 * @author Nicolas Gramlich <ngramlich@zynga.com>
 * @since 20:37:53 - 09.11.2011
 */
public class MusicException extends GameException {

	private static final long serialVersionUID = -3314204068618256639L;

	public MusicException() {
		super();
	}

	public MusicException(final String pMessage) {
		super(pMessage);
	}

	public MusicException(final Throwable pThrowable) {
		super(pThrowable);
	}

	public MusicException(final String pMessage, final Throwable pThrowable) {
		super(pMessage, pThrowable);
	}
}
