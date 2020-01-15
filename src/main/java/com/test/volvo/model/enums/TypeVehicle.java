package com.test.volvo.model.enums;

public enum TypeVehicle {
    BUS,
    TRUCK,
    CAR;
	
	public String getDescription() {
		switch (this) {
		case BUS:
			return "Bus";
		case TRUCK:
			return "Truck";
		case CAR:
			return "Car";
		default:
			return null;
		}
	}
	
	public Byte getPassengers() {
		switch (this) {
		case BUS:
			return 42;
		case TRUCK:
			return 1;
		case CAR:
			return 4;
		default:
			return null;
		}
	}
	
	public static TypeVehicle searchByDescription(String description) {
		switch (description) {
		case "Bus": 
			return BUS;
		case "Truck":
			return TRUCK;
		case "Car":
			return CAR;
		default:
			return null;
		}
	}
}
