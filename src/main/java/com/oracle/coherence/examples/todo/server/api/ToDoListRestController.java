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
    // TODO: add implementation here
    }
