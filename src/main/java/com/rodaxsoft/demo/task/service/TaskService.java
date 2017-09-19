/*
  TaskService.java

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
package com.rodaxsoft.demo.task.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.rodaxsoft.demo.task.data.TaskRepository;
import com.rodaxsoft.demo.task.domain.Task;

/**
 * TaskService class
 */
@Service
public class TaskService {
	
	
	private TaskRepository taskRepository;

	@Autowired
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	public Task createTask(Task task) {
		if(null == task.getTitle()) {
			throw new IllegalArgumentException("title cannot be null");
		}
		
		Task savedTask = taskRepository.save(task);
		return savedTask;
	}
	
	public List<Task> getTasks() {
		Sort sort = new Sort(new Order(Direction.ASC, "due").nullsFirst());
		List<Task> tasks = taskRepository.findAll(sort);
		return tasks;
	}
	
	public void deleteTask(Long id) {
		taskRepository.delete(id);
	}
	
	public Task updateTask(Task task) {
		Task savedTask = taskRepository.findOne(task.getId());
		//Non-modifiable properties: id, created, userId
		task.setId(savedTask.getId());
		task.setCreated(savedTask.getCreated());
		task.setUserId(savedTask.getUserId());
		//Ignore modified, it'll get updated
		
		savedTask = taskRepository.save(task)	;
		return savedTask;
	}
}
