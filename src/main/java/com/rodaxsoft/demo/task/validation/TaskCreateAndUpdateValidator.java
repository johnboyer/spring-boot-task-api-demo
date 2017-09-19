/*
  TaskCreateAndUpdateValidator.java

  Created by John Boyer on Sep 12, 2017
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

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.rodaxsoft.demo.task.domain.Task;
import com.rodaxsoft.demo.task.domain.TaskStatus;

/**
 * TaskCreateAndUpdateValidator class
 *
 */
public class TaskCreateAndUpdateValidator implements Validator {

	private static final String FIELD_EMPTY_MSG = "Field is empty";

	private static final String FIELD_EMPTY_CODE = "field.empty";
	
	private static final String FIELD_READONLY_MSG = "Field is readonly";
	
	private static final String FIELD_READONLY_CODE = "field.readonly";


	@Override
	public boolean supports(Class<?> clazz) {
		return Task.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "field.required", "Title required");
		Task task = (Task) target;
		
		if(task.getCreated() != null) {
			errors.rejectValue("created", FIELD_READONLY_CODE, FIELD_READONLY_MSG);			
		}
		
		if(task.getModified() != null) {
			errors.rejectValue("modified", FIELD_READONLY_CODE, FIELD_READONLY_MSG);
		}
		
		if(task.getUserId() != null) {
			errors.rejectValue("userId", FIELD_READONLY_CODE, FIELD_READONLY_MSG);
		}
		
		//Check for empty description if non-null
		String description = task.getDescription();
		if(description != null && description.isEmpty()) {
			errors.rejectValue("description", FIELD_EMPTY_CODE, FIELD_EMPTY_MSG);			
		}
		
		if(task.getId() != null) {
			errors.rejectValue("userId", FIELD_READONLY_CODE, FIELD_READONLY_MSG);
		}
		
		TaskStatus status = task.getStatus();
		if((task.getCompleted() != null && status != TaskStatus.COMPLETED) || 
		   (status != null && status == TaskStatus.RESERVED) ) {
			errors.rejectValue("status", "field.invalid", "Invalid status");			
		}
	}

}
