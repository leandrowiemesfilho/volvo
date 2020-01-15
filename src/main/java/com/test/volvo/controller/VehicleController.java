package com.test.volvo.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.test.volvo.model.Vehicle;
import com.test.volvo.payload.ApiResponse;
import com.test.volvo.payload.ChassisIdentityExistence;
import com.test.volvo.payload.PagedResponse;
import com.test.volvo.payload.VehicleRequest;
import com.test.volvo.payload.VehicleResponse;
import com.test.volvo.repository.ChassisRepository;
import com.test.volvo.security.CurrentUser;
import com.test.volvo.security.UserPrincipal;
import com.test.volvo.service.VehicleService;
import com.test.volvo.util.AppConstants;
import com.test.volvo.util.ModelMapper;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ChassisRepository chassisRepository;

    @GetMapping
    public PagedResponse<VehicleResponse> getVehicles(@CurrentUser UserPrincipal currentUser,
                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return vehicleService.getAllVehicles(currentUser, page, size);
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createVehicle(@RequestBody VehicleRequest vehicleRequest) {
        vehicleService.createVehicle(vehicleRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/")
                .build().toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Vehicle created successfully"));
    }

    @GetMapping("/{vehicleId}")
    public VehicleResponse getVehicleById(@CurrentUser UserPrincipal currentUser,
                                    @PathVariable Long vehicleId) {
        return vehicleService.getVehicleById(vehicleId);
    }
    
    @GetMapping("/checkChassisExistence")
    public ChassisIdentityExistence checkChassisExistence(@RequestParam(value = "chassis") String chassis) {
        Boolean exists = chassisRepository.existsBySeries(chassis);
        Vehicle vehicle = vehicleService.findByChassis(chassis);
        return new ChassisIdentityExistence(exists, vehicle.getColor());
    }

    @GetMapping("/search")
    public VehicleResponse searchChassisBySeries(@RequestParam(value = "chassis") String chassis) {
    	Vehicle vehicle = vehicleService.findByChassis(chassis);
    	
        return ModelMapper.mapVehicleToVehicleResponse(vehicle);
    }
    
    @GetMapping("/delete")
    public ResponseEntity<?> deleteChassisBySeries(@RequestParam(value = "chassis") String chassis) {
    	Vehicle result = vehicleService.findByChassis(chassis);
    	vehicleService.deleteVehicleChassis(result);
    	
    	URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/")
                .build().toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Vehicle deleted successfully!"));
    }
    
    @PostMapping("/edit")
    public ResponseEntity<?> editChassisBySeries(@RequestBody VehicleRequest vehicleRequest) {
    	Vehicle result = vehicleService.findByChassis(vehicleRequest.getChassisSeries());
    	result.setColor(vehicleRequest.getColor());
    	vehicleService.editVehicle(result);
    	
    	URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/")
                .build().toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Vehicle updated successfully!"));
    }
}
