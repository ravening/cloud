package com.rakeshv.hackathon.vps.service;

import com.rakeshv.hackathon.vps.domain.VirtualMachine;
import com.rakeshv.hackathon.vps.repository.VirtualMachineRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * VirtualMachineService
 */
@Service
public class VirtualMachineService {
    private final Logger log = LoggerFactory.getLogger(VirtualMachineService.class);
    @Autowired
    VirtualMachineRepository virtualMachineRepository;

    public void createVirtualMachine(VirtualMachine virtualMachine) {
        log.info("create-vps-request : Creating a virtual machine");
        virtualMachineRepository.save(virtualMachine);
    }
}