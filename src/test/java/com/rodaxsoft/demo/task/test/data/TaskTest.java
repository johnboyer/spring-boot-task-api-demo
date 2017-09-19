/*
  TaskTest.java

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

import java.io.IOException;
import java.util.Date;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodaxsoft.demo.task.domain.Task;
import com.rodaxsoft.demo.task.domain.TaskStatus;

/**
 * TaskTest class
 */
public class TaskTest {

	public static Task createMockTask() {
		Task task = new Task();
		task.setDue(new LocalDate(2017, 10, 31).toDate());
		task.setCreated(new Date());
		task.setDescription("description of Test task 1 ");
		task.setTitle("Test Task 1");
		task.setId(1L);
		return task;
	}

	@Test
	public void testCreate() {

		Task task = createMockTask();

		try {
			ObjectMapper mapper = new ObjectMapper();
			String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(task);
			System.out.println(result);
			task = mapper.readValue(result, Task.class);
			System.out.println(task);

		} catch (IOException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testUpdate() {
		Task task = createMockTask();
		task.setCompleted(new Date());
		task.setModified(new Date());
		task.setStatus(TaskStatus.COMPLETED);

		try {
			ObjectMapper mapper = new ObjectMapper();
			String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(task);
			System.out.println(result);
			task = mapper.readValue(result, Task.class);
			System.out.println(task);

		} catch (IOException e) {
			Assert.fail(e.getMessage());
		}


	}

}
