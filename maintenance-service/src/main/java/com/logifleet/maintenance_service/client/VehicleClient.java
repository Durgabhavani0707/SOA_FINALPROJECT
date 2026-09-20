package com.logifleet.maintenance_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "vehicle-service")
public interface VehicleClient {

    @PutMapping("/vehicles/{id}/status")
    void updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    );
}