package com.space.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String planet;

    @Enumerated(EnumType.STRING)
    private ShipType shipType;

    private Date prodDate;
    private Boolean isUsed;
    private Double speed;
    private Integer crewSize;
    private Double rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;

    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Boolean isUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean used) {
        isUsed = used;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Ship updateData(Ship ship) {
        if (this.getName() != null && !this.getName().equals(ship.getName()))
            this.setName(ship.getName());

        if (this.getPlanet() != null && !this.getPlanet().equals(ship.getPlanet()))
            this.setPlanet(ship.getPlanet());

        if (this.getShipType() != null  && this.getShipType() != ship.getShipType())
            this.setShipType(ship.getShipType());

        if (this.getProdDate() != null && this.getProdDate() != ship.getProdDate())
            this.setProdDate(ship.getProdDate());

        if (this.isUsed() != null && this.isUsed() != ship.isUsed)
            this.setIsUsed(ship.isUsed());

        if (this.getSpeed() != null && this.getSpeed() != ship.getSpeed())
            this.setSpeed(ship.getSpeed());

        if (this.getCrewSize() != null && this.getCrewSize() != ship.getCrewSize())
            this.setCrewSize(ship.getCrewSize());


        return this;
    }
}
