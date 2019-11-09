package com.rakeshv.hackathon.orders.messaging;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import lombok.extern.slf4j.Slf4j;

/**
 * CreateVpsResponse
 */
@EnableBinding(VpsCreateResponseBinding.class)
@Slf4j
public class CreateVpsResponse {

    @StreamListener(target = VpsCreateResponseBinding.QUEUE)
    public void getCreateVpsResponse(String message) {
        log.info("create-vps-response : Received the message: {}", message);
    }
}