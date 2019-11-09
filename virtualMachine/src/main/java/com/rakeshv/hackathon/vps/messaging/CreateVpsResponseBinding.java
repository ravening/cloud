package com.rakeshv.hackathon.vps.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * CreateVpsResponseBinding
 */
public interface CreateVpsResponseBinding {

    @Output("create-vps-response")
    MessageChannel createVpsResponse();
}