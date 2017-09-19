# Demo Task API

> __This project is intended for demonstration purposes only.__

The Task demo is a simple task management service. Each task has a name as well as other optional values including *due date*, *status*, and *description*.

The demo Task RESTful API is implemented as a [Spring Boot](https://projects.spring.io/spring-boot/) app running an embedded version of Tomcat. For demonstration purposes, the data store is a [HSQLDB](http://hsqldb.org) in-memory database accessed via [Spring JPA](https://projects.spring.io/spring-data-jpa/). Note: Spring's security functionality has been disabled in the `application.properties` file.

# Table of Contents

1. [Build and Run](#build-and-run)
2. [Reference](#reference)
4. [License](#license)

# Build and Run

To build and run the project in place type: `$ gradle bootRun`

To just build the project type: `$ gradle build`

For information on installing Gradle go to https://gradle.org/install

## Testing
The project includes integration tests for the `@RestController`, `@Service`, and `@Repository` classes in the `src/test` directory.

Once the app is running, you can use **[curl](https://curl.haxx.se)** to test and interact with the API. The curl command line tool transfers data from or to a server, using one of the supported protocols such as FTP, HTTP, HTTPS and so on. Use the following commands to create, retrieve, update, and delete tasks.

	# Create a task
	curl --header 'Content-Type: application/json' \
	     --request POST \
	     --data '{ "completed": null, "description": "My short description", "due": "2017-10-31", "status": "open", "title": "My task #1"}' \
	     --url http://localhost:8080/tasks

	# Get tasks
	curl --header 'Content-Type: application/json' \
	     --request GET \
	     --url http://localhost:8080/tasks

	# Update a task with new due date and description
	curl --header 'Content-Type: application/json' \
	     --request PUT \
	     --data '{ "completed": null, "description": "My new description", "due": "2017-11-30", "status": "open", "title": "My task #1"}' \
	     --url http://localhost:8080/tasks/1

	# Delete a task
	curl --request DELETE --url http://localhost:8080/tasks/1

# Reference
Note: All dates are specified as strings, i.e., `yyyy-MM-dd` or `yyyy-MM-dd'T'hh:mm:ss`.

## Tasks

Method | HTTP Requests | Description
------------ | ------------- |-------
*[insert](#insert-task)* | `POST /tasks` | Create a task
*[list](#get-tasks)* | `GET /tasks`| Returns tasks
*[update](#update-task)* |`PUT /tasks/:id` | Update a task
*[delete](#delete-task)* |`DELETE /tasks/:id` | Delete a task

##  Errors
If an error occurs, the API will return the following JSON response:

	{
	    "timestamp": number,
	    "status": number,
	    "error": string,
	    "exception": string,
	    "message": string,
	    "path": string
	}

---
### Insert Task

#### Request
`POST /tasks`

#### Header
`Content-Type: application/json`

#### Parameters
The `title` is required as a body element. The `status` defaults to `"open"`.

#### Request Body

	{
  	  "completed": string (yyyy-MM-dd),
  	  "description": string,
  	  "due": date string (yyyy-MM-dd),
  	  "status": string (open | completed),
 	    "title": string <required>
	}

#### Response
If successful, returns JSON task:

	{
  	  "completed": string (yyyy-MM-dd),
  	  "created": string (yyyy-MM-dd'T'hh:mm:ss),
  	  "description": string,
  	  "due": string (yyyy-MM-dd),
  	  "id": number,
  	  "modified": string (yyyy-MM-dd'T'hh:mm:ss),
  	  "status": string (open | completed),
  	  "title": string
	}

---

### Get Tasks

#### Request
`GET /tasks`

#### Header
`Content-Type: application/json`

#### Parameters
No parameters required.

#### Request Body
No body required

#### Response
If successful, returns a sorted array of tasks in ascending order by due date. Due dates with `null` values will appear first.

	[{
  	   "completed": string (yyyy-MM-dd),
  	   "created": string (yyyy-MM-dd'T'hh:mm:ss),
  	   "description": string,
  	   "due": string (yyyy-MM-dd),
  	   "id": number,
  	   "modified": string (yyyy-MM-dd'T'hh:mm:ss),
  	   "status": string (open | completed),
  	   "title": string
	}, {
  	     "completed": string (yyyy-MM-dd),
  	     "created": string (yyyy-MM-dd'T'hh:mm:ss),
  	     "description": string,
  	     "due": string (yyyy-MM-dd),
  	     "id": number,
  	     "modified": string (yyyy-MM-dd'T'hh:mm:ss),
  	     "status": string (open | completed),
  	     "title": string
	},...
	]

---

### Update Task

#### Request
`PUT /tasks/:id`

#### Header
`Content-Type: application/json`

#### Parameters
The task `id` is required as a path parameter and the `title` is required as a body element. The `status` defaults to `"open"`.

#### Request Body

	{
  	  "completed": string (yyyy-MM-dd),
  	  "description": string | null,
  	  "due": date string (yyyy-MM-dd),
  	  "status": string (open | completed),
 	    "title": string <required>
	}

#### Response
If successful, returns JSON task:

	{
  	  "completed": string (yyyy-MM-dd),
  	  "created": string (yyyy-MM-dd'T'hh:mm:ss),
  	  "description": string,
  	  "due": string (yyyy-MM-dd),
  	  "id": number,
  	  "modified": string (yyyy-MM-dd'T'hh:mm:ss),
  	  "status": string (open | completed),
  	  "title": string
	}

---

### Delete Task

#### Request
`DELETE /tasks/:id`

#### Header
No header required.

#### Parameters
The task `id` is required as a path parameter.

#### Request Body
No body required.

#### Response
No response, if successful.

# License
This Source Code Form is subject to the terms of the Apache License, v. 2.0 at http://www.apache.org/licenses/LICENSE-2.0.

_By John Boyer, Rodax Software, Inc._
