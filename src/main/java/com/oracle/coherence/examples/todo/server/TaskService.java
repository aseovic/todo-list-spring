/*
 * Copyright (c) 2021, Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * https://oss.oracle.com/licenses/upl.
 */
package com.oracle.coherence.examples.todo.server;

import java.util.Collection;

/**
 * Task service that delegates to the underlying
 * {@link com.oracle.coherence.examples.todo.server.TaskRepository}.
 */
public interface TaskService {
	Collection<Task> findAll(boolean completed);

	Task find(String id);

	void save(Task task);

	void removeById(String id);

	void deleteCompletedTasks();

	Task updateDescription(String id, String description);

	Task updateCompletionStatus(String id, boolean completed);
}
