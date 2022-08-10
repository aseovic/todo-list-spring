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
    }
