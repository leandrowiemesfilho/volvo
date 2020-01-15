package com.test.volvo.util;

import com.test.volvo.model.Vehicle;
import com.test.volvo.payload.VehicleResponse;

public class ModelMapper {

    public static VehicleResponse mapVehicleToVehicleResponse(Vehicle vehicle) {
        VehicleResponse vehicleResponse = new VehicleResponse();
        vehicleResponse.setColor(vehicle.getColor());
        vehicleResponse.setPassengers(vehicle.getPassengers().toString());
        vehicleResponse.setType(vehicle.getType().getDescription());
        vehicleResponse.setChassisSeries(vehicle.getChassis().getSeries());
        vehicleResponse.setChassisNumber(vehicle.getChassis().getNumber().toString());
        
        return vehicleResponse;
    }

}
