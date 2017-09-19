/*
  TaskTestUtils.java

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
package com.rodaxsoft.demo.task.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;

import com.rodaxsoft.demo.task.domain.Task;


/**
 * TaskTestUtils class
 *
 */
public class TaskTestUtils {


	public static final String DESCRIPTION = "Description of Test task 1 ";
	public static final Date DUE_DATE = new LocalDate(2017, 10, 31).toDate();
	public static final String TITLE = "Test Task 1";

	public static List<Task> create100Tasks() {
		List<Task> tasks = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Task task = createMockTask();
			if(i > 0 && i < 80) {
				Date lastDue = tasks.get(i-1).getDue();
				LocalDate ld = new LocalDate(lastDue.getTime());
				ld = ld.plusDays(1);
				task.setDue(ld.toDate());
			} else if(i >= 80){
				task.setDue(null);
			}

			tasks.add(task);
		}
		return tasks;
	}

	public static Task createMockTask() {
		Task task = new Task();
		task.setDue(DUE_DATE);
		task.setDescription(DESCRIPTION);
		task.setTitle(TITLE);
		return task;
	}

}
