package com.logifleet.trip_service.controller;

import com.logifleet.trip_service.client.MaintenanceClient;
import com.logifleet.trip_service.client.MaintenanceRequest;
import com.logifleet.trip_service.client.MaintenanceResponse;
import com.logifleet.trip_service.client.VehicleClient;
import com.logifleet.trip_service.client.VehicleResponse;
import com.logifleet.trip_service.entity.Trip;
import com.logifleet.trip_service.repository.TripRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripRepository repository;
    private final VehicleClient vehicleClient;
    private final MaintenanceClient maintenanceClient;

    public TripController(
            TripRepository repository,
            VehicleClient vehicleClient,
            MaintenanceClient maintenanceClient) {

        this.repository = repository;
        this.vehicleClient = vehicleClient;
        this.maintenanceClient = maintenanceClient;
    }

    // Create / Assign Trip
    @PostMapping
    public Trip addTrip(@RequestBody Trip trip) {

        VehicleResponse vehicle =
                vehicleClient.getVehicle(trip.getVehicleId());

        if (vehicle == null) {
            throw new RuntimeException("Vehicle not found");
        }

        if (!"AVAILABLE".equals(vehicle.getStatus())) {
            throw new RuntimeException("Vehicle is not available");
        }

        vehicleClient.updateStatus(
                trip.getVehicleId(),
                "ON_TRIP"
        );

        trip.setStatus("ASSIGNED");
        trip.setProgress(0);
        trip.setCurrentLocation(
                trip.getStartLocation()
        );

        return repository.save(trip);
    }

    // Get all trips
    @GetMapping
    public List<Trip> getAllTrips() {
        return repository.findAll();
    }

    // Get trip by ID
    @GetMapping("/{id}")
    public Trip getTrip(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    // Delete trip
    @DeleteMapping("/{id}")
    public String deleteTrip(@PathVariable Long id) {
        repository.deleteById(id);
        return "Trip deleted successfully";
    }

    // Start Trip
    @PutMapping("/{id}/start")
    public Trip startTrip(@PathVariable Long id) {

        Trip trip = repository.findById(id).orElse(null);

        if (trip == null) {
            return null;
        }

        if (!"ASSIGNED".equals(trip.getStatus())) {
            throw new RuntimeException(
                    "Trip is not in ASSIGNED status"
            );
        }

        trip.setStatus("IN_PROGRESS");

        return repository.save(trip);
    }

    // Update Trip Progress
    @PutMapping("/{id}/progress")
    public Trip updateProgress(
            @PathVariable Long id,
            @RequestBody Trip progressData) {

        Trip trip = repository.findById(id).orElse(null);

        if (trip == null) {
            return null;
        }

        if (!"IN_PROGRESS".equals(trip.getStatus())) {
            throw new RuntimeException(
                    "Trip is not in IN_PROGRESS status"
            );
        }

        trip.setCurrentLocation(
                progressData.getCurrentLocation()
        );

        trip.setProgress(
                progressData.getProgress()
        );

        return repository.save(trip);
    }

    // Complete Trip
    @PutMapping("/{id}/complete")
    public Trip completeTrip(@PathVariable Long id) {

        Trip trip =
                repository.findById(id).orElse(null);

        if (trip == null) {
            return null;
        }

        if (!"IN_PROGRESS".equals(trip.getStatus())) {
            throw new RuntimeException(
                    "Trip is not in IN_PROGRESS status"
            );
        }

        trip.setStatus("COMPLETED");
        trip.setProgress(100);

        vehicleClient.updateStatus(
                trip.getVehicleId(),
                "AVAILABLE"
        );

        return repository.save(trip);
    }

    // Get Vehicle through Trip Service
    @GetMapping("/vehicle/{id}")
    public VehicleResponse getVehicle(@PathVariable Long id) {
        return vehicleClient.getVehicle(id);
    }

    // Create Maintenance Work Order
    @PostMapping("/{id}/maintenance")
    public MaintenanceResponse createMaintenance(
            @PathVariable Long id,
            @RequestBody MaintenanceRequest request) {

        Trip trip = repository.findById(id).orElse(null);

        if (trip == null) {
            throw new RuntimeException("Trip not found");
        }

        request.setVehicleId(trip.getVehicleId());

        if (request.getStatus() == null) {
            request.setStatus("OPEN");
        }

        // Create maintenance work order
        MaintenanceResponse response =
                maintenanceClient.createMaintenance(request);

        // Change vehicle status
        vehicleClient.updateStatus(
                trip.getVehicleId(),
                "UNDER_MAINTENANCE"
        );

        return response;
    }

    // Test Controller
    @GetMapping("/test")
    public String test() {
        return "Trip Controller is working";
    }
}																																			