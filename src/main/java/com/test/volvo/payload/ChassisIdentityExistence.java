package com.test.volvo.payload;

public class ChassisIdentityExistence {
    private Boolean exists;
    private String color;

    public ChassisIdentityExistence(Boolean exists, String color) {
        this.exists = exists;
        this.color = color;
    }

	public Boolean getExists() {
		return exists;
	}

	public void setExists(Boolean exists) {
		this.exists = exists;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
