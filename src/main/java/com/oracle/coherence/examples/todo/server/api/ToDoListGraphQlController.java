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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ToDoListGraphQlController
    {
    @Autowired
    private TaskService taskService;

    /**
     * Create a task with the given description.
     * @return the newly created task
     */
    @MutationMapping
    public Task createTask(@Argument String description)
        {
        return taskService.createTask(description);
        }

    /**
     * Find a single task by the provided id.
     *
     * @param id the id of the task to find
     * @return the retrieved task
     */
    @QueryMapping
    public Task findTask(@Argument String id)
        {
        return taskService.findTask(id);
        }

    /**
     * Retrieve tasks based on completion status.
     * @param completed if true return only completed tasks
     * @return a collection of tasks
     */
    @QueryMapping
    public Collection<Task> tasks(@Argument Boolean completed)
        {
        return taskService.findTasks(completed);
        }

    /**
     * Remove all completed tasks.
     * @return {@code true} if any tasks have been removed
     */
    @MutationMapping
    public Boolean deleteCompletedTasks()
        {
        return taskService.deleteCompletedTasks();
        }

    /**
     * Delete a task and return the deleted task details.
     * @return the deleted task
     */
    @MutationMapping
    public Task deleteTask(@Argument String id)
        {
        return taskService.deleteTask(id);
        }

    /**
     * Update a task.
     * @return the updated task
     */
    @MutationMapping
    public Task updateDescription(@Argument String id, @Argument String description)
        {
        return taskService.updateDescription(id, description);
        }

    /**
     * Update a task.
     * @return the updated task
     */
    @MutationMapping
    public Task updateCompletionStatus(@Argument String id, @Argument Boolean completed)
        {
        return taskService.updateCompletionStatus(id, completed);
        }
    }

