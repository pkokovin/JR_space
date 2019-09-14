package com.space.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private long id;

    @Basic
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Basic
    @Column(nullable = false, unique = true, length = 50)
    private String planet;

    @Basic
    @Column(nullable = false, length = 9)
    @Enumerated(EnumType.STRING)
    private ShipType shipType;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(unique = true)
    private Date prodDate;

    private Boolean isUsed;
    private Double speed;

    @Basic
    @Column(nullable = false, length = 4)
    private Integer crewSize;
    private Double rating;

    protected Ship() {
    }

    public Ship(String name, String planet, ShipType shipType, Date prodDate, Boolean isUsed, Double speed, Integer crewSize) {
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.prodDate = prodDate;
        this.isUsed = isUsed;
        this.speed = speed;
        this.crewSize = crewSize;
        this.rating = calcRating(prodDate, isUsed, speed);
    }

    public Ship(String name, String planet, ShipType shipType, Date prodDate, Double speed, Integer crewSize) {
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.prodDate = prodDate;
        this.isUsed = false;
        this.speed = speed;
        this.crewSize = crewSize;
        this.rating = calcRating(prodDate, false, speed);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setUsed(Boolean used) {
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

    public double calcRating(Date date, boolean isUsed, double speed) {
        double k = isUsed ? 0.5 : 1.0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentYear = 3019;
        double result = (80*speed*k)/(currentYear-calendar.get(Calendar.YEAR)+1.0);
        BigDecimal btw = BigDecimal.valueOf(result).setScale(2, BigDecimal.ROUND_HALF_UP);
        return btw.doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ship)) return false;
        Ship ship = (Ship) o;
        return getName().equals(ship.getName()) &&
                getPlanet().equals(ship.getPlanet()) &&
                getProdDate().equals(ship.getProdDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPlanet(), getProdDate());
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
}
