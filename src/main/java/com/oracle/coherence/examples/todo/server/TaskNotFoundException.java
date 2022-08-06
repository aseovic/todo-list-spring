/*
 * Copyright (c) 2021 Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * https://oss.oracle.com/licenses/upl.
 */
package com.oracle.coherence.examples.todo.server;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

/**
 * An exception indicating that a {@link Task} was not found.
 */
public class TaskNotFoundException
        extends RuntimeException implements GraphQLError
    {
    private static final String MESSAGE_PREFIX = "Unable to find task with id ";

    /**
     * Create the exception.
     *
     * @param message reason for the exception.
     */
    public TaskNotFoundException(String taskId)
        {
        super(MESSAGE_PREFIX + taskId);
        }

        @Override
        public List<SourceLocation> getLocations()
            {
            return null;
            }

        @Override
        public ErrorClassification getErrorType()
            {
            return null;
            }
    }
