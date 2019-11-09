package com.rakeshv.hackathon.orders.messaging;

import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * CreateVpsPublisher
 */
@EnableBinding(VpsCreateRequestBinding.class)
public class CreateVpsPublisher {
}