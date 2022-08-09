/*
 * Copyright (c) 2021, Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * https://oss.oracle.com/licenses/upl.
 */
package com.oracle.coherence.examples.todo.server.api;

import java.util.Collection;

import com.oracle.coherence.examples.todo.server.Task;
import com.oracle.coherence.examples.todo.server.TaskService;
import com.oracle.coherence.spring.annotation.event.*;
import com.oracle.coherence.spring.event.CoherenceEventListener;
import com.tangosol.util.MapEvent;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * REST API for To Do List management.
 */
@RestController
@RequestMapping(path="/api/tasks",
                produces = MediaType.APPLICATION_JSON_VALUE)
public class ToDoListRestController
    {
    private final TaskService taskService;

    private final SseService sseService;

    public ToDoListRestController(TaskService taskService, SseService sseService)
        {
        this.taskService = taskService;
        this.sseService = sseService;
        }

    @CoherenceEventListener
    public void broadCastEvents(@MapName("tasks") @Inserted @Updated @Deleted MapEvent<String, Task> event)
        {
        final String eventName;
        final Task taskToSend;

        if (event.isInsert())
            {
            eventName = "insert";
            taskToSend = event.getNewValue();
            }
        else if (event.isUpdate())
            {
            eventName = "update";
            taskToSend = event.getNewValue();
            }
        else if (event.isDelete())
            {
            eventName = "delete";
            taskToSend = event.getOldValue();
            }
        else
            {
            throw new IllegalStateException("Unsupported event " + event);
            }

        this.sseService.sendEventToClients(eventName, taskToSend);
        }

    @GetMapping
    public Collection<Task> getTasks(@RequestParam Boolean completed)
        {
        return taskService.findTasks(completed);
        }

    @PostMapping
    public Task createTask(@RequestBody Task task)
        {
        return taskService.createTask(task.getDescription());
        }

    @DeleteMapping("/{id}")
    public Task deleteTask(@PathVariable String id)
        {
        return taskService.deleteTask(id);
        }

    @DeleteMapping
    public void deleteCompletedTasks()
        {
        taskService.deleteCompletedTasks();
        }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody Task task)
        {
        Task result = null;

        String  description = task.getDescription();
        Boolean completed   = task.getCompleted();
        if (description != null)
            {
            result = taskService.updateDescription(id, description);
            }
        else if (completed != null)
            {
            result = taskService.updateCompletionStatus(id, completed);
            }

        return result == null
               ? taskService.findTask(id)
               : result;
        }

    @GetMapping(path = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registerSseClient()
        {
        return sseService.registerClient();
        }
    }
