package implementations;

import java.util.List;
import java.util.logging.Logger;

import entities.EnumTripType;
import entities.Trip;
import exception.LogicException;
import interfaces.TripManager;
import java.util.logging.Level;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import restful.TripRESTclient;

/**
 * This class implements {@link TripManager} business logic interface using a
 * RESTful web client to access business logic in a Java EE application server.
 */
public class TripManagerImplementation implements TripManager {
    // Rest trips webclient
    private TripRESTclient webClient;

    /**
     * Logger object used to log messages for application.
     */
    private static final Logger LOGGER = Logger.getLogger(TripManagerImplementation.class.getName());

    /**
     * Create a TripManagerImplementation object. It constructs a web client for
     * accessing a RESTful service that provides business logic in an application
     * server.
     */
    public TripManagerImplementation() {
        webClient = new TripRESTclient();
    }

    /**
     * This method returns a {@link Trip} object, containing the trip objerct for the given id.
     * @param id The id of the trip to find.
     * @return Trip The trip object for the given id.
     * @throws LogicException If there is any error while processing.
     */
    @Override
    public Trip findTripById(Integer id) throws LogicException {
        try {
            LOGGER.info("TripManager: Finding trip by id from REST service (XML).");
            return webClient.find(Trip.class, id.toString());
        } catch (WebApplicationException ex) {
            LOGGER.log(Level.SEVERE, "TripManager: Exception finding trip by id, {0}", ex.getMessage());
            throw new LogicException("Error finding trip by id:\n" + ex.getMessage());
        }
    }

    /**
     * This method creates a new trip using the given {@link Trip} object.
     * @param trip The Trip object to be created.
     * @throws LogicException If there is any error while processing.
     */
    @Override
    public void createTrip(Trip trip) throws LogicException {
        try {
            LOGGER.info("TripManager: Creating a new trip via REST service (XML).");
            webClient.create(trip);
        } catch (WebApplicationException ex) {
            LOGGER.log(Level.SEVERE, "TripManager: Exception creating a new trip, {0}", ex.getMessage());
            throw new LogicException("Error creating a new trip:\n" + ex.getMessage());
        }
    }

    /**
     * This method updates an existing trip using the given {@link Trip} object.
     * @param trip The Trip object to be updated.
     * @throws LogicException If there is any error while processing.
     */
    @Override
    public void updateTrip(Trip trip) throws LogicException {
        try {
            LOGGER.info("TripManager: Updating trip via REST service (XML).");
            webClient.update(trip);
        } catch (WebApplicationException ex) {
            LOGGER.log(Level.SEVERE, "TripManager: Exception updating trip, {0}", ex.getMessage());
            throw new LogicException("Error updating trip:\n" + ex.getMessage());
        }
    }

    /**
     * This method deletes an existing trip using the given {@link Trip} object.
     * @param trip The Trip object to be deleted.
     * @throws LogicException If there is any error while processing.
     */
    @Override
    public void deleteTrip(Trip trip) throws LogicException {
        try {
            LOGGER.info("TripManager: Deleting trip via REST service (XML).");
            webClient.delete(trip.getId().toString());
        } catch (WebApplicationException ex) {
            LOGGER.log(Level.SEVERE, "TripManager: Exception deleting trip, {0}", ex.getMessage());
            throw new LogicException("Error deleting trip:\n" + ex.getMessage());
        }
    }

    /**
     * This method returns a list of all trips.
     * @return List<Trip> The list of all trips.
     * @throws LogicException If there is any error while processing.
     */
    @Override
    public List<Trip> findAllTrips() throws LogicException {
        try {
            LOGGER.info("TripManager: Finding all trips from REST service (XML).");
            return webClient.findAllTrips(new GenericType<List<Trip>>() {});
        } catch (WebApplicationException ex) {
            LOGGER.log(Level.SEVERE, "TripManager: Exception finding all trips, {0}", ex.getMessage());
            throw new LogicException("Error finding all trips:\n" + ex.getMessage());
        }
    }

    /**
     * This method returns a list of trips filtered by trip type.
     * @param tripType The type of trips to filter by.
     * @return List<Trip> The list of filtered trips.
     * @throws LogicException If there is any error while processing.
     */
    @Override
    public List<Trip> findTripsByTripType(EnumTripType tripType) throws LogicException {
        try {
            LOGGER.info("TripManager: Finding trips by trip type from REST service (XML).");
            return webClient.findTripsByTripType(new GenericType<List<Trip>>() {}, tripType.name());
        } catch (WebApplicationException ex) {
            LOGGER.log(Level.SEVERE, "TripManager: Exception finding trips by trip type, {0}", ex.getMessage());
            throw new LogicException("Error finding trips by trip type:\n" + ex.getMessage());
        }
    }
}
