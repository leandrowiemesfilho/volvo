package com.test.volvo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.volvo.model.Chassis;
import com.test.volvo.model.Vehicle;

@Repository
public interface ChassisRepository extends JpaRepository<Chassis, Long> {

    public Optional<Chassis> findById(Long chassisId);

    public Boolean existsBySeries(String series);
    
    public Chassis findBySeries(String series);

    public List<Vehicle> findByIdIn(List<Long> chassisIds);
    
    public List<Vehicle> findByIdIn(List<Long> chassisIds, Sort sort);
}
