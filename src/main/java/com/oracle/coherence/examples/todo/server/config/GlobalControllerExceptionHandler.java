/*
 * Copyright (c) 2021, Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * https://oss.oracle.com/licenses/upl.
 */
package com.oracle.coherence.examples.todo.server.config;

import com.oracle.coherence.examples.todo.server.TaskNotFoundException;

import java.util.Map;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler
    {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler
    public Map<String, String> handle(TaskNotFoundException e)
        {
        return Map.of("error", e.getMessage());
        }
    }
