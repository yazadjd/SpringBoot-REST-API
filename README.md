# Stomble Task

We develop a REST API to manage the logistics of Stomble’s fleet of spaceships.
The API will store information about the different locations as well as the
spaceships stationed at those locations. We create a Controller that contains
the URI endpoints with the help of SpringBoot and test the functionality
using Swagger.

Assumptions:
- Travel to any location happens in a single trip.
- All spaceships are stationed at some existent location.
- Travel happens instantaneously.

The API fulfills the following use cases:
-	Add spaceships: a spaceship must have an id, name, model, location (made up of a city and a planet) and its status (decommissioned, maintenance or operational).
-	Update the spaceship status: to one of the 3 possible states.
-	Add a location: a location must have an id, city name and a planet name; as well as the spaceport capacity (how many spaceships can be stationed at this location simultaneously).
-	Remove spaceships: given a spaceship’s id.
-	Remove location: given a location’s id.
-	Travel functionality: Travel involves changing the location of the spaceship. Before carrying out the travel transaction, check these two factors:
    -	The spaceport capacity of the destination (if not, return an appropriate error).
    -	The status of the spaceship (only operational spaceships can travel).


On pulling the code and performing a ```mvn clean install```, a jar file will automatically be created.
