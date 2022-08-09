/*
 * Copyright (c) 2021, Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * https://oss.oracle.com/licenses/upl.
 */
package com.oracle.coherence.examples.todo.server;

import com.tangosol.util.Filter;
import com.tangosol.util.Filters;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * A {@link TaskService} to interact with the underlying {@link TaskRepository}
 * for working with {@link Task tasks}.
 */
@Service
public class ToDoListService
        implements TaskService
    {
    private final TaskRepository tasks;

    public ToDoListService(TaskRepository tasks)
        {
        this.tasks = tasks;
        }

    @Override
    public Collection<Task> findTasks(Boolean completed)
        {
        final Filter<Task> filter = completed == null
                                    ? Filters.always()
                                    : Filters.equal(Task::getCompleted, completed);

        return tasks.getAllOrderedBy(filter, Task::getCreatedAt);
        }

    @Override
    public Task createTask(String description)
        {
        Assert.notNull(description, "The description is required.");
        return tasks.save(new Task(description));
        }

    @Override
    public Task findTask(String id)
        {
        Assert.hasText(id, "The Task Id must not be null or empty.");
        return tasks.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        }

    @Override
    public Task deleteTask(String id)
        {
        Assert.hasText(id, "The Task Id must not be null or empty.");
        Map<String, Task> mapRemoved = tasks.deleteAllById(Set.of(id), true);
        return Optional.ofNullable(mapRemoved.get(id))
                .orElseThrow(() -> new TaskNotFoundException(id));
        }

    @Override
    public boolean deleteCompletedTasks()
        {
        return tasks.deleteAll(Filters.isTrue(Task::getCompleted));
        }

    /**
     * Update a {@link Task} description.
     *
     * @param id           task to update
     * @param description  new description
     *
     * @return the updated {@link Task}
     *
     * @throws TaskNotFoundException if the task was not found
     */
    public Task updateDescription(String id, String description)
        {
        Task task = tasks.update(id, Task::setDescription, description);
        return Optional
                .ofNullable(task)
                .orElseThrow(() -> new TaskNotFoundException(id));
        }

    /**
     * Update a {@link Task} completion status.
     *
     * @param id         task to update
     * @param completed  new completion status
     *
     * @return the updated {@link Task}
     *
     * @throws TaskNotFoundException if the task was not found
     */
    public Task updateCompletionStatus(String id, boolean completed)
        {
        Task task = tasks.update(id, Task::setCompleted, completed);
        return Optional
                .ofNullable(task)
                .orElseThrow(() -> new TaskNotFoundException(id));
        }
    }
