package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "A video with that id was not found")
public class VideoNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 2368177623393784859L;

	public VideoNotFoundException(String message) {
		super(message);
	}
}
