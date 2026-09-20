package com.logifleet.trip_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "maintenance-service")
public interface MaintenanceClient {

    @PostMapping("/maintenance")
    MaintenanceResponse createMaintenance(
            @RequestBody MaintenanceRequest request
    );
}