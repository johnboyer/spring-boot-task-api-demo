/*
  TaskControllerTest.java

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
package com.rodaxsoft.demo.task.test.rest;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rodaxsoft.demo.task.TaskApplication;
import com.rodaxsoft.demo.task.data.TaskRepository;
import com.rodaxsoft.demo.task.domain.Task;
import com.rodaxsoft.demo.task.service.TaskService;
import com.rodaxsoft.demo.task.test.TaskTestUtils;
import com.rodaxsoft.demo.task.test.UploadableTestTask;

/**
 * TaskControllerTest class
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT,
  classes = TaskApplication.class)
@AutoConfigureMockMvc
public class TaskControllerTest {


	private static final String TASKS_ENDPOINT = "/tasks";

	@Autowired
	private MockMvc mvc;

	private MockHttpServletResponse response;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskService taskService;


	@After
	public void cleanup() {
		taskRepository.deleteAll();
	}

	@Test
	public void testCreateTask() {
		try {

			Task task = TaskTestUtils.createMockTask();
			String json = new ObjectMapper().writeValueAsString(task);
			MvcResult result = mvc.perform(post(TASKS_ENDPOINT)
					                .contentType(MediaType.APPLICATION_JSON)
					                .content(json))
					                            .andExpect(status().isOk())
					                            .andExpect(jsonPath("$.id", notNullValue()))
					                            .andExpect(jsonPath("$.due", notNullValue()))
					                            .andExpect(jsonPath("$.title", notNullValue()))
					                            .andExpect(jsonPath("$.status", notNullValue()))
					                            .andExpect(jsonPath("$.created", notNullValue()))
					                            .andDo(print())
					               .andReturn();

			response = result.getResponse();

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void testDeleteTask() {
		Task task = TaskTestUtils.createMockTask();
		task = taskService.createTask(task);


		try {
			MvcResult result;
			result = mvc.perform(delete(TASKS_ENDPOINT + "/" + task.getId())
			        .contentType(MediaType.TEXT_PLAIN_VALUE))
			                    .andExpect(status().isOk())
			                    .andDo(print())
			       .andReturn();
			response = result.getResponse();
		} catch (Exception e) {
			Assert.fail(e.getMessage());

		}
	}

	@Test
	public void testGetTasks() {
		List<Task> tasks = TaskTestUtils.create100Tasks();

		MvcResult result;
		try {

			result = mvc.perform(get(TASKS_ENDPOINT)
			        .contentType(MediaType.APPLICATION_JSON))
			                    .andExpect(status().isOk())
	                            .andExpect(jsonPath("$[*].id", notNullValue()))
	                            .andExpect(jsonPath("$[*].due", notNullValue()))
	                            .andExpect(jsonPath("$[*].title", notNullValue()))
	                            .andExpect(jsonPath("$[*].status", notNullValue()))
	                            .andExpect(jsonPath("$[*].created", notNullValue()))
			                    .andDo(print())
			       .andReturn();

			response = result.getResponse();

			ObjectMapper mapper = new ObjectMapper();
			TypeFactory factory = mapper.getTypeFactory();
			CollectionType type = factory.constructCollectionType(List.class, Task.class);
			String json = response.getContentAsString();
			tasks = mapper.readValue(json, type);

			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("test-tasks.json"), tasks);

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testUpdateTask() {
		Task task = TaskTestUtils.createMockTask();
		task = taskService.createTask(task);
		task.setDescription("All new description");
		task.setTitle("My brand new title");

		UploadableTestTask upldTask = new UploadableTestTask();
		BeanUtils.copyProperties(task, upldTask);

		try {

			String json = new ObjectMapper().writeValueAsString(upldTask);
			MvcResult result = mvc.perform(put(TASKS_ENDPOINT + "/" + task.getId())
			        .contentType(MediaType.APPLICATION_JSON)
			        .content(json))
			                    .andExpect(status().isOk())
			                    .andExpect(jsonPath("$.id", notNullValue()))
	                            .andExpect(jsonPath("$.due", notNullValue()))
	                            .andExpect(jsonPath("$.title", notNullValue()))
	                            .andExpect(jsonPath("$.status", notNullValue()))
	                            .andExpect(jsonPath("$.created", notNullValue()))
	                            .andExpect(jsonPath("$.description", notNullValue()))
	                            .andExpect(jsonPath("$.modified", notNullValue()))
			                    .andDo(print())
			       .andReturn();

			response = result.getResponse();

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
