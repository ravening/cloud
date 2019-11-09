package com.rakeshv.hackathon.orders.service;

import java.util.List;

import com.rakeshv.hackathon.orders.domain.OrderVps;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * VpsService
 */
@FeignClient("ADMINDASHBOARD")
public interface VpsService {

    @GetMapping("/api/vps")
    public List<OrderVps> getAllVpsPacksList();
}