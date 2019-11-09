package com.rakeshv.hackathon.orders.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * VpsCreateResponseBinding
 */
public interface VpsCreateResponseBinding {

    String QUEUE = "create-vps-response";
    @Input(QUEUE)
    SubscribableChannel createVpsResponse();
}