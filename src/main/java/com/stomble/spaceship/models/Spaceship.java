/*
Author: Yazad Davur <yazadjd@yahoo.com>

This file defines the Spaceship class that contains a spaceship id, name,
model, status and the location of type location of the spaceship. The class
also contains appropriate methods to get and set the values of the class
attributes.
 */

package com.stomble.spaceship.models;

public class Spaceship {
    private long id;
    private String name;
    private String model;
    private String status;
    private Location location;

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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
