package com.test.volvo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.test.volvo.model.audit.UserDateAudit;
import com.test.volvo.model.enums.TypeVehicle;

@Entity
@Table(name = "vehicle")
public class Vehicle extends UserDateAudit {
	private static final long serialVersionUID = -6388438374444720248L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@OneToOne(fetch=FetchType.LAZY, optional=false, orphanRemoval=false)
	private Chassis chassis;

	@Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private TypeVehicle type;
	
    private Byte passengers;
	
    @NotBlank
    @Size(max = 140)
    private String color;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public TypeVehicle getType() {
		return type;
	}

	public void setType(TypeVehicle type) {
		this.type = type;
	}

	public Byte getPassengers() {
		return passengers;
	}

	public void setPassengers(Byte passengers) {
		this.passengers = passengers;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Chassis getChassis() {
		return chassis;
	}

	public void setChassis(Chassis chassis) {
		this.chassis = chassis;
	}
}
