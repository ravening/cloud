package com.rakeshv.hackathon.orders.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * VpsCreateRequestBinding
 */
public interface VpsCreateRequestBinding {

    String QUEUE = "create-vps-request";

    @Output(QUEUE)
    MessageChannel createVpsRequest();
}