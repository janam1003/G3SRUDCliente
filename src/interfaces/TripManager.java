package interfaces;

import entities.EnumTripType;
import java.util.List;
import entities.Trip;
import exception.LogicException;

/**
 * Logic interface encapsulating business methods for trip management.
 * @author Iñigo
 */
public interface TripManager {
    /**
     * Finds a {@link Trip} by its id.
     *
     * @param id The id for the trip to be found.
     * @return The {@link Trip} object containing trip data.
     * @throws LogicException If there is any Exception during processing.
     */
    public Trip findTripById(Integer id) throws LogicException;

    /**
     * Creates a Trip and stores it in the underlying application storage.
     *
     * @param trip The {@link Trip} object containing the trip data.
     * @throws LogicException If there is any Exception during processing.
     */
    public void createTrip(Trip trip) throws LogicException;

    /**
     * Updates a trip's data in the underlying application storage.
     *
     * @param trip The {@link Trip} object containing the trip data.
     * @throws LogicException If there is any Exception during processing.
     */
    public void updateTrip(Trip trip) throws LogicException;

    /**
     * Deletes a trip's data in the underlying application storage.
     *
     * @param trip The {@link Trip} object containing the trip data.
     * @throws LogicException If there is any Exception during processing.
     */
    public void deleteTrip(Trip trip) throws LogicException;

    /**
     * Finds a List of {@link Trip} objects containing data for all trips in the
     * application data storage.
     *
     * @return A List of {@link Trip} objects.
     * @throws LogicException If there is any Exception during processing.
     */
    public List<Trip> findAllTrips() throws LogicException;

    /**
     * Finds a List of {@link Trip} objects containing data for all trips with a
     * certain tripType value.
     *
     * @param tripType The tripType value for the trips to be found.
     * @return A List of {@link Trip} objects.
     * @throws LogicException If there is any Exception during processing.
     */
    public List<Trip> findTripsByTripType(EnumTripType tripType) throws LogicException;   
}
