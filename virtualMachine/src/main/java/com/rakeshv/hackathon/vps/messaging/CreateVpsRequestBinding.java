package com.rakeshv.hackathon.vps.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * CreateVpsRequestBinding
 */
public interface CreateVpsRequestBinding {

    String QUEUE = "create-vps-request";

    @Input(QUEUE)
    SubscribableChannel createVpsRequest();
}