package com.space.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", planet='" + planet + '\'' +
                ", shipType=" + shipType +
                ", prodDate=" + prodDate +
                ", isUsed=" + isUsed +
                ", speed=" + speed +
                ", crewSize=" + crewSize +
                ", rating=" + rating +
                '}';
    }

    public Ship updateData(Ship ship) {
        if (ship.getName() != null )
            this.setName(ship.getName());

        if (ship.getPlanet() != null /*&& !this.getPlanet().equals(ship.getPlanet())*/)
            this.setPlanet(ship.getPlanet());

        if (ship.getShipType() != null  /*&& this.getShipType() != ship.getShipType()*/)
            this.setShipType(ship.getShipType());

        if (ship.getProdDate() != null /*&& this.getProdDate() != ship.getProdDate()*/)
            this.setProdDate(ship.getProdDate());

        if (ship.isUsed() != null /*&& this.isUsed() != ship.isUsed*/)
            this.setIsUsed(ship.isUsed());

        if (ship.getSpeed() != null /*&& this.getSpeed() != ship.getSpeed()*/)
            this.setSpeed(Math.round(ship.getSpeed() * 100.00 ) / 100.00);

        if (ship.getCrewSize() != null /*&& this.getCrewSize() != ship.getCrewSize()*/)
            this.setCrewSize(ship.getCrewSize());


        return this;
    }
}
