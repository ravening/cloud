package com.rakeshv.hackathon.vps.service;

import com.rakeshv.hackathon.vps.domain.VirtualMachine;
import com.rakeshv.hackathon.vps.repository.VirtualMachineRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * VirtualMachineService
 */
@Service
@Slf4j
public class VirtualMachineService {
    @Autowired
    VirtualMachineRepository virtualMachineRepository;

    public void createVirtualMachine(VirtualMachine virtualMachine) {
        log.info("create-vps-request : Creating a virtual machine");
        virtualMachineRepository.save(virtualMachine);
    }
}