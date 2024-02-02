package interfaces;

import entities.Customer;
import entities.Trip;
import entities.TripInfo;
import entities.TripInfoId;
import exception.LogicException;
import java.util.List;

/**
 * Logic interface encapsulating business methods for tripInfo management.
 *
 * @author Iñigo
 */
public interface TripInfoManager {

    /**
     * Finds a {@link TripInfo} by its Id.
     *
     * @param id The Id for the TripInfo to be found.
     * @return The {@link TripInfo} object containing tripInfo data
     * @throws LogicException If there is any Exception during processing.
     */
    public TripInfo findTripInfoById(TripInfoId tripInfoId) throws LogicException;

    /**
     * Finds all {@link TripInfo} objects associated with a given Customer.
     *
     * @param customer The Customer for which to retrieve TripInfo objects.
     * @return A List of {@link TripInfo} objects.
     * @throws LogicException If there is any Exception during processing.
     */
    public List<TripInfo> findAllTripInfoByCustomer(Customer customer) throws LogicException;

    /**
     * Finds all {@link TripInfo} objects associated with a given Trip.
     *
     * @param trip The Trip for which to retrieve TripInfo objects.
     * @return A List of {@link TripInfo} objects.
     * @throws LogicException If there is any Exception during processing.
     */
    public List<TripInfo> findAllTripInfoByTrip(Trip trip) throws LogicException;

    /**
     * Updates the information of a TripInfo in the underlying application
     * storage.
     *
     * @param tripInfo The {@link TripInfo} object containing the updated data.
     * @throws LogicException If there is any Exception during processing.
     */
    public void updateTripInfo(TripInfo tripInfo) throws LogicException;

    /**
     * Deletes a TripInfo from the underlying application storage.
     *
     * @param tripInfo The {@link TripInfo} object to be deleted.
     * @throws LogicException If there is any Exception during processing.
     */
    public void deleteTripInfo(TripInfo tripInfo) throws LogicException;

    /**
     * Creates a new TripInfo and stores it in the underlying application
     * storage.
     *
     * @param tripInfo The {@link TripInfo} object containing the new data.
     * @throws LogicException If there is any Exception during processing.
     */
    public void createTripInfo(TripInfo tripInfo) throws LogicException;

    /**
     * Finds all active {@link TripInfo} objects associated with a given
     * Customer.
     *
     * @param customer The Customer for which to retrieve active TripInfo
     * objects.
     * @return A List of active {@link TripInfo} objects.
     * @throws LogicException If there is any Exception during processing.
     */
    public List<TripInfo> findActiveTripInfoByCustomer(Customer customer) throws LogicException;

    /**
     * Finds all inactive {@link TripInfo} objects associated with a given
     * Customer.
     *
     * @param customer The Customer for which to retrieve inactive TripInfo
     * objects.
     * @return A List of inactive {@link TripInfo} objects.
     * @throws LogicException If there is any Exception during processing.
     */
    public List<TripInfo> findInactiveTripInfoByCustomer(Customer customer) throws LogicException;
}
