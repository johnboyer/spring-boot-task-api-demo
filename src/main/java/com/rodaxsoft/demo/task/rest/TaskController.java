/*
  TaskController.java

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
package com.rodaxsoft.demo.task.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodaxsoft.demo.task.domain.Task;
import com.rodaxsoft.demo.task.service.TaskService;
import com.rodaxsoft.demo.task.validation.TaskCreateAndUpdateValidator;
import com.rodaxsoft.demo.task.validation.ValidationException;

/**
 * TaskController class
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

	private TaskService taskService;
	@Autowired
	private TaskCreateAndUpdateValidator taskValidator;

	@Autowired
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@PostMapping
	public Task createTask(@RequestBody Task task, BindingResult result) {
		taskValidator.validate(task, result);
		if(result.hasErrors()) {
			throw new ValidationException(result);
		}

		return taskService.createTask(task);
	}

	@GetMapping
	public List<Task> getTasks() {
		return taskService.getTasks();
	}

	@PutMapping(path = "/{id}")
	public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask, BindingResult result) {
		taskValidator.validate(updatedTask, result);
		if (result.hasFieldErrors()) {
			throw new ValidationException(result);
		}

		updatedTask.setId(id);
		return taskService.updateTask(updatedTask);
	}

	 @DeleteMapping(path = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
		public String deleteTask(@PathVariable Long id) {
			taskService.deleteTask(id);
			return "";
		}

}
