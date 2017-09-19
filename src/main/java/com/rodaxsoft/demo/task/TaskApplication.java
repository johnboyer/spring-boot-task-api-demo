/*
  TaskApplication.java

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
package com.rodaxsoft.demo.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.rodaxsoft.demo.task.validation.TaskCreateAndUpdateValidator;

@SpringBootApplication
public class TaskApplication {

	@Bean
	public TaskCreateAndUpdateValidator taskCreateAndUpdateValidator() {
		return new TaskCreateAndUpdateValidator();
	}

	public static void main(String[] args) {

		SpringApplication.run(TaskApplication.class, args);
	}
}
