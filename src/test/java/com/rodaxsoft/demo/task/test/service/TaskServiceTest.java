/*
  TaskServiceTest.java

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
package com.rodaxsoft.demo.task.test.service;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodaxsoft.demo.task.data.TaskRepository;
import com.rodaxsoft.demo.task.domain.Task;
import com.rodaxsoft.demo.task.domain.TaskStatus;
import com.rodaxsoft.demo.task.service.TaskService;
import com.rodaxsoft.demo.task.test.TaskTestUtils;

/**
 * TaskServiceTest class
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {
	@Autowired
	private TaskRepository taskRepsitory;

	@Autowired
	private TaskService taskService;

	private Long userId;


	@After
	public void cleanup() {
		taskRepsitory.deleteAll();
	}

	/**
	 * @return
	 */
	private Task createMockTask() {
		Task task = new Task();
		task.setDue(new LocalDate(2017, 10, 31).toDate());
		task.setDescription("Description of Test task 1 ");
		task.setTitle("Test Task 1");
		task.setUserId(userId);
		return task;
	}

	@Test
	public void testCreateTask() {
		Task savedTask = taskService.createTask(createMockTask());
		System.out.println(savedTask);
		Assert.assertNotNull(savedTask.getCreated());
		Assert.assertNotNull(savedTask.getId());

		try {
			String result = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(savedTask);
			System.out.println(result);
		} catch (JsonProcessingException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testDeleteTask() {
		Task savedTask = taskService.createTask(createMockTask());
		taskService.deleteTask(savedTask.getId());
		System.out.println(savedTask);
		assertTrue(taskService.getTasks().isEmpty());

		try {
			String result = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(savedTask);
			System.out.println(result);
		} catch (JsonProcessingException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testGetTasks() {
		List<Task> tasks = TaskTestUtils.create100Tasks();
		Task prev = null;
		for (Task task : tasks) {
			Task savedTask = taskService.createTask(task);
			System.out.println(savedTask);
			Assert.assertNotNull(savedTask.getCreated());
			Assert.assertNotNull(savedTask.getId());

			if(prev != null && prev.getDue() != null && task.getDue() != null) {
				Assert.assertTrue(prev.getDue().before(task.getDue()));
			}

			prev = task;
		}

		tasks = taskService.getTasks();
		System.out.println(tasks);
	}

	@Test
	public void testUpdateTask() {
		Task savedTask = taskService.createTask(createMockTask());
		savedTask.setStatus(TaskStatus.COMPLETED);
		savedTask.setCompleted(new Date());
		savedTask = taskService.updateTask(savedTask);
		System.out.println(savedTask);
		Assert.assertNotNull(savedTask.getModified());

		try {
			String result = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(savedTask);
			System.out.println(result);
		} catch (JsonProcessingException e) {
			Assert.fail(e.getMessage());
		}
	}

}
