package com.rakeshv.hackathon.orders.service;

import com.rakeshv.hackathon.orders.messaging.VpsCreateRequestBinding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * CreateVpsService
 */
@Service
@Slf4j
public class CreateVpsService {

    @Autowired
    private VpsCreateRequestBinding createVpsMessage;

    public void createVps() {
        log.info("create-vps-request : Creating a new vps");
        Message<String> message = MessageBuilder.withPayload("Hello Rakesh").build();
        createVpsMessage.createVpsRequest().send(message);
    }
}