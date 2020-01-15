package com.test.volvo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.volvo.model.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    public Optional<Vehicle> findById(Long vehicleId);

    public Page<Vehicle> findByCreatedBy(Long userId, Pageable pageable);
    
    public long countByCreatedBy(Long userId);
    
    public List<Vehicle> findByIdIn(List<Long> vehocleIds);
    
    public List<Vehicle> findByIdIn(List<Long> vehicleIds, Sort sort);
}
