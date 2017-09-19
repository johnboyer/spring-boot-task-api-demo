/*
  ValidationException.java

  Created by John Boyer on Sep 18, 2017
  Copyright (c) 2017 Rodax Software, Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
package com.rodaxsoft.demo.task.validation;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

/**
 * ValidationException class
 */
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private static String makeExceptionMessage(Errors errors) {
		String msg = null;
		if(errors.hasFieldErrors()) {
			List<FieldError> fieldErrors = errors.getFieldErrors();
			msg = fieldErrors.stream().map(FieldError::toString).collect(Collectors.joining(","));
		}
		
		return msg;
	}
	
	/**
	 * CTOR
	 * @param result The validation binding result
	 */
	public ValidationException(Errors errors) {
		this(makeExceptionMessage(errors));
	}

	/**
	 * CTOR
	 * @param message The error message
	 */
	public ValidationException(String string) {
		super(string);
	}
	
}
