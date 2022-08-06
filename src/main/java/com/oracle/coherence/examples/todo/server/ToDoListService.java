/*
 * Copyright (c) 2021, Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * https://oss.oracle.com/licenses/upl.
 */
package com.oracle.coherence.examples.todo.server;

import com.tangosol.util.Filter;
import com.tangosol.util.Filters;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * A {@link TaskService} to interact with the underlying {@link TaskRepository}
 * for working with {@link Task tasks}.
 */
@Service
@Profile("!coherence")
public class ToDoListService
        implements TaskService
    {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToDoListService.class);

    private final TaskRepository tasks;

    public ToDoListService(TaskRepository tasks)
        {
        this.tasks = tasks;
        }

    @Override
    public Collection<Task> findAll(boolean completed)
        {
        final Filter<Task> filter = !completed
                                    ? Filters.always()
                                    : Filters.equal(Task::getCompleted, true);

        return tasks.getAllOrderedBy(filter, Task::getCreatedAt);
        }

    @Override
    public Task find(String id)
        {
        Assert.hasText(id, "The Task Id must not be null or empty.");
        return tasks.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        }

    @Override
    public void save(Task task)
        {
        Assert.notNull(task, "The task must not be null.");
        tasks.save(task);
        }

    @Override
    public void removeById(String id)
        {
        Assert.hasText(id, "The Task Id must not be null or empty.");
        tasks.deleteById(id);
        }

    @Override
    public void deleteCompletedTasks()
        {
        tasks.deleteAll(Filters.equal(Task::getCompleted, true), false);
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

    @PostConstruct
    public void init()
        {
        LOGGER.info("Using SpringDataTaskService.");
        }
    }
