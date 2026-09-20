package com.logifleet.vehicle_service.controller;

import com.logifleet.vehicle_service.entity.Vehicle;
import com.logifleet.vehicle_service.repository.VehicleRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleRepository repository;

    public VehicleController(VehicleRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Vehicle addVehicle(@RequestBody Vehicle vehicle) {
        return repository.save(vehicle);
    }

    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Vehicle getVehicle(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public String deleteVehicle(@PathVariable Long id) {
        repository.deleteById(id);
        return "Vehicle deleted successfully";
    }

    @PutMapping("/{id}/status")
    public Vehicle updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Vehicle vehicle = repository.findById(id).orElse(null);

        if (vehicle == null) {
            return null;
        }

        vehicle.setStatus(status);
        return repository.save(vehicle);
    }
}