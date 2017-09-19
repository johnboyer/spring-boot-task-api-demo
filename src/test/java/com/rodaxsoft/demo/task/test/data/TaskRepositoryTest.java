/*
  TaskRepositoryTest.java

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
package com.rodaxsoft.demo.task.test.data;

import static com.rodaxsoft.demo.task.test.TaskTestUtils.DESCRIPTION;
import static com.rodaxsoft.demo.task.test.TaskTestUtils.DUE_DATE;
import static com.rodaxsoft.demo.task.test.TaskTestUtils.TITLE;
import static com.rodaxsoft.demo.task.test.TaskTestUtils.createMockTask;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodaxsoft.demo.task.data.TaskRepository;
import com.rodaxsoft.demo.task.domain.Task;
import com.rodaxsoft.demo.task.test.TaskTestUtils;

/**
 * TaskRepositoryTest class
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskRepositoryTest {

	@Autowired
	private TaskRepository taskRepository;

	@After
	public void cleanup() {
		taskRepository.deleteAll();
	}


	@Test
	public void testGetTasks() {
		//Save a task
		Task task = createMockTask();
		task = taskRepository.save(task);

		List<Task> tasks = taskRepository.findAll();
		Assert.assertSame(1, tasks.size());
		Assert.assertSame(1L, tasks.get(0).getId());

		try {
			String result = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(tasks);
			System.out.println(result);
		} catch (JsonProcessingException e) {
			Assert.fail(e.getMessage());
		}
	}


	@Test
	public void testSaveTask() {

		Task task = TaskTestUtils.createMockTask();

		task = taskRepository.save(task);
		System.out.println();
		System.out.println(task);
		System.out.println();

		Assert.assertSame(DESCRIPTION, task.getDescription());
		Assert.assertSame(TITLE, task.getTitle());
		Assert.assertSame(DUE_DATE, task.getDue());

		try {
			String result = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(task);
			System.out.println(result);
		} catch (JsonProcessingException e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void testUpdateTask() {

		// Save a task
		Task task = createMockTask();
		task = taskRepository.save(task);

		final String desc = "An all new description";
		task.setDescription(desc);
		final String title = "The title has changed";
		task.setTitle(title);

		//Update the task
		task = taskRepository.save(task);
		Assert.assertSame(desc, task.getDescription());
		Assert.assertSame(title, task.getTitle());
		Assert.assertSame(TaskTestUtils.DUE_DATE, task.getDue());

		try {
			String result = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(task);
			System.out.println(result);
		} catch (JsonProcessingException e) {
			Assert.fail(e.getMessage());
		}

	}

}
