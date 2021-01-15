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
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Controller {

    List<Location> locationList = new ArrayList<>();
    List<Spaceship> spaceshipList = new ArrayList<>();


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

        else {
            List<Spaceship> filteredList = spaceshipList.stream().filter(spaceship1 -> spaceship1.getId() == spaceshipRequest.getId()).collect(Collectors.toList());
            if (filteredList.isEmpty()) {
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


    @RequestMapping(value = "/addLocation", method = RequestMethod.POST)
    public ResponseEntity<String> addLocation(@RequestBody Location location) {

        List<Location> filteredList = locationList.stream().filter(location1 -> location1.getId() == location.getId()).collect(Collectors.toList());
        if (filteredList.isEmpty()) {
            if (location.getCity().isEmpty()) {
                return new ResponseEntity<String>("Location not found", HttpStatus.BAD_REQUEST);
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

    @RequestMapping(value = "/removeLocation", method = RequestMethod.GET)
    public ResponseEntity<String> removeLocation(@RequestParam(value = "id") long id) {
        List<Location> filteredList = locationList.stream().filter(location -> location.getId() == id).collect(Collectors.toList());
        if (filteredList.isEmpty()) {
            return new ResponseEntity<String>("Location Not Found", HttpStatus.BAD_REQUEST);
        }
        else {
            locationList.remove((filteredList.get(0)));
            List<Spaceship> spcFilteredList = spaceshipList.stream().filter(spaceship -> spaceship.getLocation().getId() == id).collect(Collectors.toList());
            if (spcFilteredList.size() >= 1) {
                for(int i = 0 ; i < spcFilteredList.size(); i++) {
                    spaceshipList.remove((spcFilteredList.get(i)));
                }
            }

            return new ResponseEntity<String>("Location Removed", HttpStatus.OK);
        }
    }

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
