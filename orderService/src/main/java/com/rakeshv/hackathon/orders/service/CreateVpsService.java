package com.rakeshv.hackathon.orders.service;

import com.rakeshv.hackathon.orders.messaging.VpsCreateRequestBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * CreateVpsService
 */
@Service
public class CreateVpsService {
    private final Logger log = LoggerFactory.getLogger(CreateVpsService.class);
    @Autowired
    private VpsCreateRequestBinding createVpsMessage;

    public void createVps() {
        log.info("create-vps-request : Creating a new vps");
        Message<String> message = MessageBuilder.withPayload("Hello Rakesh").build();
        createVpsMessage.createVpsRequest().send(message);
    }
}