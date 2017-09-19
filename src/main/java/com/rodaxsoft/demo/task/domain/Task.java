/*
  Task.java

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
package com.rodaxsoft.demo.task.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Task class
 *
 */
@Entity
public class Task {

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date completed;


	@JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
	private Date created;

	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date due;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
	private Date modified;

	private TaskStatus status = TaskStatus.OPEN;
	
	private String title;
	
	@JsonIgnore
	private Long userId;

	/**
	 * @return the completed
	 */
	public Date getCompleted() {
		return completed;
	}
	
	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
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
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @return the modified
	 */
	public Date getModified() {
		return modified;
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
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}


	@PrePersist
	protected void onCreate() {
		created = new Date();
	}
	@PreUpdate
	protected void onUpdate() {
		setModified(new Date());
	}
	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(Date completionDate) {
		this.completed = completionDate;
	}
	/**
	 * @param created the created to set
	 */
	public void setCreated(Date createdDate) {
		this.created = createdDate;
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
	public void setDue(Date dueDate) {
		this.due = dueDate;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param modified the modified to set
	 */
	public void setModified(Date modifiedDate) {
		this.modified = modifiedDate;
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

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Task [completed=" + completed + ", created=" + created + ", description=" + description + ", due="
				+ due + ", id=" + id + ", modified=" + modified + ", status=" + status + ", title=" + title + "]";
	}
	
}

