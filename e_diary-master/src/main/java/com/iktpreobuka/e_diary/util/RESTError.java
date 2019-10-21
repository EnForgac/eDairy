package com.iktpreobuka.e_diary.util;

import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class RESTError {

	private String message;

	public RESTError(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	// Validation message
	public static String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" \n"));
	}

}
