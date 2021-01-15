/*
Author: Yazad Davur <yazadjd@yahoo.com>

This file contains the class used for adding a spaceship given a spaceship Id,
location id, name, status and model. The class also contains the appropriate
get and set methods for the class attributes.
 */
package com.stomble.spaceship.models;

public class SpaceshipRequest {
    private long id;
    private String name;
    private String model;
    private String status;
    private long locId;

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

    public long getLocId() {
        return locId;
    }

    public void setLocId(long locId) {
        this.locId = locId;
    }
}
