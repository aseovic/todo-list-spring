/*
 * Copyright (c) 2021, Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * https://oss.oracle.com/licenses/upl.
 */
package com.oracle.coherence.examples.todo.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry-point to the To Do Application.
 */
@SpringBootApplication
public class Application
    {
    public static void main(String[] args)
        {
        SpringApplication.run(Application.class, args);
        }
    }
