package com.test.volvo.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.test.volvo.exception.BadRequestException;
import com.test.volvo.exception.ResourceNotFoundException;
import com.test.volvo.model.Chassis;
import com.test.volvo.model.Vehicle;
import com.test.volvo.model.enums.TypeVehicle;
import com.test.volvo.payload.PagedResponse;
import com.test.volvo.payload.VehicleRequest;
import com.test.volvo.payload.VehicleResponse;
import com.test.volvo.repository.ChassisRepository;
import com.test.volvo.repository.VehicleRepository;
import com.test.volvo.security.CurrentUser;
import com.test.volvo.security.UserPrincipal;
import com.test.volvo.util.AppConstants;
import com.test.volvo.util.ModelMapper;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    
    @Autowired
    private ChassisRepository chassisRepository;

    public PagedResponse<VehicleResponse> getAllVehicles(@CurrentUser UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Vehicle> vehicles = vehicleRepository.findAll(pageable);

        if(vehicles.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), vehicles.getNumber(),
                    vehicles.getSize(), vehicles.getTotalElements(), vehicles.getTotalPages(), vehicles.isLast());
        }

        List<VehicleResponse> vehiclesResponses = vehicles.getContent().stream().filter(v -> v.getCreatedBy() == currentUser.getId())
        		.map(vehicle -> {
        			return ModelMapper.mapVehicleToVehicleResponse(vehicle);
        		}).collect(Collectors.toList());
        		
        return new PagedResponse<>(vehiclesResponses, vehicles.getNumber(),
        		vehicles.getSize(), vehicles.getTotalElements(), vehicles.getTotalPages(), vehicles.isLast());
    }


    public Vehicle createVehicle(VehicleRequest vehicleRequest) {
        Vehicle vehicle = new Vehicle();
        vehicle.setColor(vehicleRequest.getColor());

        TypeVehicle type = TypeVehicle.searchByDescription(vehicleRequest.getType());
        vehicle.setType(type);
        vehicle.setPassengers(type.getPassengers());

        Chassis chassis = new Chassis();
        chassis.setNumber(Integer.parseInt(vehicleRequest.getChassisNumber()));
        chassis.setSeries(vehicleRequest.getChassisSeries());
        chassisRepository.save(chassis);
        
        vehicle.setChassis(chassis);
        
        return vehicleRepository.save(vehicle);
    }

    public Vehicle findByChassis(String chassisSeries) {
    	Chassis chassis = chassisRepository.findBySeries(chassisSeries);
    	Optional<Vehicle> vehicle = vehicleRepository.findAll()
    			.stream()
    			.filter(
    					v -> v.getChassis()
    					.equals(chassis))
    			.findFirst();
    	
    	if(vehicle.isPresent()) {
    		return vehicle.get();
    	} else {
    		return new Vehicle();
    	}
    	
    }
    
    public void deleteVehicleChassis(Vehicle vehicle) {
    	vehicleRepository.delete(vehicle);
    	chassisRepository.delete(vehicle.getChassis());
    }
    
    public void editVehicle(Vehicle vehicle) {
    	vehicleRepository.save(vehicle);
    }
    
    public VehicleResponse getVehicleById(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", vehicleId));

        return ModelMapper.mapVehicleToVehicleResponse(vehicle);
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Número de páginas não pode ser menor que zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Número de páginas deve ser maior que " + AppConstants.MAX_PAGE_SIZE);
        }
    }
    
    public long countByCreatedBy(Long userId) {
    	return vehicleRepository.countByCreatedBy(userId);
    };
}
