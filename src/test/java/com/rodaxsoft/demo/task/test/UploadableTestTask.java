/*
  UploadableTestTask.java

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

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rodaxsoft.demo.task.domain.TaskStatus;

/**
 * UploadableTestTask class
 */
public class UploadableTestTask {
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date completed;

	private String description;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date due;
	private TaskStatus status;
	private String title;
	/**
	 * @return the completed
	 */
	public Date getCompleted() {
		return completed;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return the due
	 */
	public Date getDue() {
		return due;
	}
	/**
	 * @return the status
	 */
	public TaskStatus getStatus() {
		return status;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(Date completed) {
		this.completed = completed;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @param due the due to set
	 */
	public void setDue(Date due) {
		this.due = due;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
