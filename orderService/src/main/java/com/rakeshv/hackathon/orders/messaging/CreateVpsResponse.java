package com.rakeshv.hackathon.orders.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * CreateVpsResponse
 */
@EnableBinding(VpsCreateResponseBinding.class)
public class CreateVpsResponse {
    private final Logger log = LoggerFactory.getLogger(VpsCreateResponseBinding.class);
    @StreamListener(target = VpsCreateResponseBinding.QUEUE)
    public void getCreateVpsResponse(String message) {
        log.info("create-vps-response : Received the message: {}", message);
    }
}