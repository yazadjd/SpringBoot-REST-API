/*
Author : Yazad Davur <yazadjd@yahoo.com.>

The controller is a Springboot Rest controller which accepts requests from the
mapped endpoints, and returns responses  back. The responses for each endpoint
are objects of ResponseEntity and return a response with the appropriate
Status code (200 for success, 400 for bad requests).
 */

package com.stomble.spaceship.controller;

import com.stomble.spaceship.models.Location;
import com.stomble.spaceship.models.Spaceship;
import com.stomble.spaceship.models.SpaceshipRequest;
import io.swagger.models.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNullApi;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Controller {

    List<Location> locationList = new ArrayList<>(); //List of all valid locations
    List<Spaceship> spaceshipList = new ArrayList<>(); //List of all valid spaceships
    List<String> statusList = Arrays.asList("operational", "decommissioned", "maintenance");

    /*
    The add method adds a spaceship given a 'unique' spaceship ID. It also accepts a name,
    model, location (by id) and status (operational, decommissioned, maintenance).
    Spaceship is added to a particular location only if the current capacity of the
    location is less than the maximum capacity of that location.
     */
    @RequestMapping(value="/addSpaceship", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody SpaceshipRequest spaceshipRequest)    {

        List<Location> filteredLocations = locationList.stream().filter(location -> location.getId() == spaceshipRequest.getLocId()).collect(Collectors.toList());
        if (filteredLocations.isEmpty()) {
            return new ResponseEntity<String>("Location not found", HttpStatus.BAD_REQUEST);
        }

        long maxCapacity = filteredLocations.get(0).getCapacity();
        List<Spaceship> spcFilteredList = spaceshipList.stream().filter(spaceship -> spaceship.getLocation().getId() == spaceshipRequest.getLocId()).collect(Collectors.toList());
        long currCapacity = spcFilteredList.size();

        if (spaceshipRequest.getStatus().isEmpty()) {
            return new ResponseEntity<String>("Status not found", HttpStatus.BAD_REQUEST);
        }
        else if (spaceshipRequest.getModel().isEmpty()) {
            return new ResponseEntity<String>("Model not found", HttpStatus.BAD_REQUEST);
        }
        else if (spaceshipRequest.getName().isEmpty()) {
            return new ResponseEntity<String>("Name not found", HttpStatus.BAD_REQUEST);
        }
        else if (currCapacity >= maxCapacity) {
            return new ResponseEntity<String>("Location already at full capacity.", HttpStatus.BAD_REQUEST);
        }
        else if (!(statusList.contains(spaceshipRequest.getStatus()))) {
            return new ResponseEntity<String>("Status Invalid", HttpStatus.BAD_REQUEST);
        }
        else {
            List<Spaceship> filteredList = spaceshipList.stream().filter(spaceship1 -> spaceship1.getId() == spaceshipRequest.getId()).collect(Collectors.toList());
            if (filteredList.isEmpty()) { //Add Spaceship
                Spaceship spaceship = new Spaceship();
                spaceship.setLocation(filteredLocations.get(0));
                spaceship.setStatus(spaceshipRequest.getStatus());
                spaceship.setId(spaceshipRequest.getId());
                spaceship.setModel(spaceshipRequest.getModel());
                spaceship.setName(spaceshipRequest.getName());
                spaceshipList.add(spaceship);
            }
            else {
                return new ResponseEntity<String>("Spaceship already exists with this ID", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<String>("Sucessfully Added", HttpStatus.OK);
    }


    /*
    The update method takes the spaceship ID and updates the status of the spaceship
    given that it already exists.
     */
    @RequestMapping(value="/updateSpaceship", method = RequestMethod.GET)
    public ResponseEntity<String> update(@RequestParam(value = "id") long id,
            @RequestParam(value = "status") String status) {
        List<Spaceship> filteredList = spaceshipList.stream().filter(spaceship -> spaceship.getId() == id).collect(Collectors.toList());
        if (filteredList.isEmpty()) {
            return new ResponseEntity<String>("Spaceship Not Found", HttpStatus.BAD_REQUEST);
        }
        else {
            filteredList.get(0).setStatus(status);
            return new ResponseEntity<String>("Sucessfully Updated Status", HttpStatus.OK);
        }
    }

    /*
    The addLocation method takes a unique location id, city name, planet name and spaceport capacity
    as input and adds it to the list of valid locations.
     */
    @RequestMapping(value = "/addLocation", method = RequestMethod.POST)
    public ResponseEntity<String> addLocation(@RequestBody Location location) {

        List<Location> filteredList = locationList.stream().filter(location1 -> location1.getId() == location.getId()).collect(Collectors.toList());
        if (filteredList.isEmpty()) {
            if (location.getCity().isEmpty()) {
                return new ResponseEntity<String>("City not found", HttpStatus.BAD_REQUEST);
            }
            if (location.getPlanet().isEmpty()) {
                return new ResponseEntity<String>("Planet not found", HttpStatus.BAD_REQUEST);
            }
            else {
                locationList.add(location);
                return new ResponseEntity<String>("Sucessfully Added Location", HttpStatus.OK);
            }
        }
        else {
            return new ResponseEntity<String>("Location already exists", HttpStatus.BAD_REQUEST);
        }
    }
    /*
    The remove Spaceship method takes a spaceship id as input and removes the spaceship
    if it exists.
     */
    @RequestMapping(value = "/removeSpaceship", method = RequestMethod.GET)
    public ResponseEntity<String> removeSpaceship(@RequestParam(value = "id") long id) {
        List<Spaceship> filteredList = spaceshipList.stream().filter(spaceship -> spaceship.getId() == id).collect(Collectors.toList());
        if (filteredList.isEmpty()) {
            return new ResponseEntity<String>("Spaceship Not Found", HttpStatus.BAD_REQUEST);
        }
        else {
            spaceshipList.remove((filteredList.get(0)));
            return new ResponseEntity<String>("Spaceship Removed", HttpStatus.OK);
        }
    }
    /*
    The remove location method takes a location id and checks if the location is valid.
    If the location is valid, it first removes all spaceships from that location and
    then deletes the location.
     */
    @RequestMapping(value = "/removeLocation", method = RequestMethod.GET)
    public ResponseEntity<String> removeLocation(@RequestParam(value = "id") long id) {
        List<Location> filteredList = locationList.stream().filter(location -> location.getId() == id).collect(Collectors.toList());
        if (filteredList.isEmpty()) {
            return new ResponseEntity<String>("Location Not Found", HttpStatus.BAD_REQUEST);
        }
        else {
            List<Spaceship> spcFilteredList = spaceshipList.stream().filter(spaceship -> spaceship.getLocation().getId() == id).collect(Collectors.toList());
            if (spcFilteredList.size() >= 1) {
                for(int i = 0 ; i < spcFilteredList.size(); i++) {
                    spaceshipList.remove((spcFilteredList.get(i)));
                }
            }
            locationList.remove((filteredList.get(0)));
            return new ResponseEntity<String>("Location Removed", HttpStatus.OK);
        }
    }
    /*
    The travel method takes the destination location id and spaceship id and changes
     the location of the spaceship instantaneously after checking if the destination
     location has available capacity and the spaceship status is 'operational'.
     The capacity at a particular location is calculated on demand basis.
     */
    @RequestMapping(value = "/travel", method = RequestMethod.GET)
    public ResponseEntity<String> travel(@RequestParam(value = "locId") long locId,
                                         @RequestParam(value="spcID") long spcId){
        List<Location> locFilteredList = locationList.stream().filter(location -> location.getId() == locId).collect(Collectors.toList());
        if(locFilteredList.isEmpty()) {
            return new ResponseEntity<String >("Destination does not exist", HttpStatus.BAD_REQUEST);
        }
        long maxCapacity = locFilteredList.get(0).getCapacity();

        List<Spaceship> spcFilteredList = spaceshipList.stream().filter(spaceship -> spaceship.getLocation().getId() == locId).collect(Collectors.toList());
        long currCapacity = spcFilteredList.size();

        List<Spaceship> filteredList = spaceshipList.stream().filter(spaceship -> spaceship.getId() == spcId).collect(Collectors.toList());

        if (filteredList.isEmpty()) {
            return new ResponseEntity<String >("Spaceship does not exist", HttpStatus.BAD_REQUEST);
        }
        String status = filteredList.get(0).getStatus();

        if(locFilteredList.get(0).getId() == filteredList.get(0).getLocation().getId()) {
            return new ResponseEntity<String >("Spaceship is already at the destination location.", HttpStatus.BAD_REQUEST);
        }

        else if (currCapacity >= maxCapacity) {
            return new ResponseEntity<String >("Destination Capacity Filled", HttpStatus.BAD_REQUEST);
        }
        else if (!status.equalsIgnoreCase("operational")) {
            return new ResponseEntity<String >("Spaceship NOT Operational", HttpStatus.BAD_REQUEST);
        }
        filteredList.get(0).setLocation(locFilteredList.get(0));
        return new ResponseEntity<String >("Travel Successful", HttpStatus.OK);
    }
}
