/*
 * Copyright (c) 2021, Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * https://oss.oracle.com/licenses/upl.
 */
package com.oracle.coherence.examples.todo.server.api;

import com.oracle.coherence.examples.todo.server.Task;
import com.tangosol.net.Cluster;
import com.tangosol.net.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Service for managing server-sent events.
 */
@Service
public class SseService
    {
    @Autowired
    private Cluster cluster;

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter registerClient()
        {
        final SseEmitter emitter = new SseEmitter();
        final Member member = cluster.getLocalMember();

        try
            {
            emitter.send(SseEmitter.event()
                   .name("begin")
                   .data(member.toString(), MediaType.TEXT_PLAIN));
            emitter.send("begin");
            }
        catch (IOException ex)
            {
            throw new IllegalStateException("Error registering SseEmitter.", ex);
            }

        this.emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));

        emitter.onTimeout(() ->
            {
            emitter.complete();
            emitters.remove(emitter);
            });

        return emitter;
    }

    public void sendEventToClients(String name, Task task)
        {
        final List<SseEmitter> deadEmitters = new ArrayList<>();
        emitters.forEach(emitter ->
            {
            try
                {
                emitter.send(SseEmitter.event()
                       .name(name)
                       .data(task, MediaType.APPLICATION_JSON));
                }
            catch (IOException ex)
                {
                deadEmitters.add(emitter);
                }
            });

        if (deadEmitters.isEmpty())
            {
            emitters.removeAll(deadEmitters);
            }
        }
    }
