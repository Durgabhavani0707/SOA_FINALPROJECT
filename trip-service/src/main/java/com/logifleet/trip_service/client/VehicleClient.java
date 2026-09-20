package com.logifleet.trip_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "vehicle-service")
public interface VehicleClient {

    @GetMapping("/vehicles/{id}")
    VehicleResponse getVehicle(@PathVariable Long id);

    @PutMapping("/vehicles/{id}/status")
    VehicleResponse updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    );
}