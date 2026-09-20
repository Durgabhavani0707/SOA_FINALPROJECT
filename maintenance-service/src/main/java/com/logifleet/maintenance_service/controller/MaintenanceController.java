package com.logifleet.maintenance_service.controller;

import com.logifleet.maintenance_service.client.VehicleClient;
import com.logifleet.maintenance_service.entity.Maintenance;
import com.logifleet.maintenance_service.repository.MaintenanceRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {

    private final MaintenanceRepository repository;
    private final VehicleClient vehicleClient;

    public MaintenanceController(
            MaintenanceRepository repository,
            VehicleClient vehicleClient) {

        this.repository = repository;
        this.vehicleClient = vehicleClient;
    }

    @PostMapping
    public Maintenance addMaintenance(
            @RequestBody Maintenance maintenance) {

        return repository.save(maintenance);
    }

    @GetMapping
    public List<Maintenance> getAllMaintenance() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Maintenance getMaintenance(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public String deleteMaintenance(@PathVariable Long id) {

        repository.deleteById(id);

        return "Maintenance deleted successfully";
    }

    // Complete Maintenance
    @PutMapping("/{id}/complete")
    public Maintenance completeMaintenance(
            @PathVariable Long id) {

        Maintenance maintenance =
                repository.findById(id).orElse(null);

        if (maintenance == null) {
            return null;
        }

        // Mark maintenance as completed
        maintenance.setStatus("COMPLETED");

        Maintenance savedMaintenance =
                repository.save(maintenance);

        // Make vehicle available again
        vehicleClient.updateStatus(
                maintenance.getVehicleId(),
                "AVAILABLE"
        );

        return savedMaintenance;
    }
}