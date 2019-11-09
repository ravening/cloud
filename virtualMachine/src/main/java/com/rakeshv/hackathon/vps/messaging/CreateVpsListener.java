package com.rakeshv.hackathon.vps.messaging;

import java.math.BigDecimal;

import com.rakeshv.hackathon.vps.domain.VirtualMachine;
import com.rakeshv.hackathon.vps.service.VirtualMachineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * CreateVpsListener
 */
@EnableBinding(CreateVpsRequestBinding.class)
@Slf4j
public class CreateVpsListener {
    @Autowired
    CreateVpsResponseBinding createVpsResponse;
    @Autowired
    VirtualMachineService virtualMachineService;

    @StreamListener(target = CreateVpsRequestBinding.QUEUE)
    public void createVpsRequest(String message) {
        log.info("create-vps-request : Received a request to create a new vps with message : {}", message);
        log.info("create-vps-request: Please wait while the vps is being deployed");  

        VirtualMachine virtualMachine = new VirtualMachine();
        BigDecimal cpu = new BigDecimal(1);
        BigDecimal ram = new BigDecimal(1024);
        BigDecimal disk = new BigDecimal(40.00);
        BigDecimal traffic = new BigDecimal(6.00);
        virtualMachine.setName("small-instance");
        virtualMachine.setCpu(cpu);
        virtualMachine.setRam(ram);
        virtualMachine.setAccountName("admin");
        virtualMachine.setDisk(disk);
        virtualMachine.setTraffic(traffic);
        virtualMachine.setTemplate("ubuntu");

        virtualMachineService.createVirtualMachine(virtualMachine);
        log.info("create-vps-request : VPS has been deployed.. sending the response back : {}", message);
        Message<String> message2 = MessageBuilder.withPayload(virtualMachine.toString()).build();
        createVpsResponse.createVpsResponse().send(message2);
    }
}