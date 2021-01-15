/*
Author: Yazad Davur <yazadjd@yahoo.com>

This file defines the Location class that contains the Location id, city name,
planet name and the spaceport capacity as class attributes. The class also
contains appropriate methods to get and set the values of the class attributes.
 */

package com.stomble.spaceship.models;

public class Location {
    private long id;
    private String city;
    private String planet;
    private long capacity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }
}
