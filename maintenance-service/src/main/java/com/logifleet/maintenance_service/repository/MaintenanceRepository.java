package com.logifleet.maintenance_service.repository;

import com.logifleet.maintenance_service.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

}