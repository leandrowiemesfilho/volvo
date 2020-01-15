package com.test.volvo.payload;

public class VehicleRequest {
    private String color;
    private String type;
    private String chassisSeries;
    private String chassisNumber;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChassisSeries() {
		return chassisSeries;
	}

	public void setChassisSeries(String chassisSeries) {
		this.chassisSeries = chassisSeries;
	}

	public String getChassisNumber() {
		return chassisNumber;
	}

	public void setChassisNumber(String chassisNumber) {
		this.chassisNumber = chassisNumber;
	}
}
