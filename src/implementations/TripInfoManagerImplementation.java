/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations;

import entities.Customer;
import entities.Trip;
import entities.TripInfo;
import entities.TripInfoId;
import exception.LogicException;
import interfaces.TripInfoManager;
import java.util.List;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import restful.TripInfoRESTClient;

/**
 *
 * @author 2dam
 */
public class TripInfoManagerImplementation implements TripInfoManager {

    private TripInfoRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("TripInfoManagerImplementation");

    public TripInfoManagerImplementation() {
        webClient = new TripInfoRESTClient();
    }

    @Override
    public TripInfo findTripInfoById(TripInfoId tripInfoId) throws LogicException {
        try {
            LOGGER.info("TripInfoManager: Finding trip info by id from REST service (XML).");
            return webClient.find(TripInfo.class, tripInfoId.getCustomerId(), tripInfoId.getTripId().toString());
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE, "TripInfoManager: Exception finding trip info by id, {0}", ex.getMessage());
            throw new LogicException("Error finding trip info by id:\n" + ex.getMessage());
        }
    }

    @Override
    public List<TripInfo> findAllTripInfoByCustomer(Customer customer) throws LogicException {
        try {
            LOGGER.info("TripInfoManager: Finding all trip info by customer from REST service (XML).");
            return webClient.findAllTripInfoByCustomer(new GenericType<List<TripInfo>>() {}, customer.getMail());
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE, "TripInfoManager: Exception finding all trip info by customer, {0}", ex.getMessage());
            throw new LogicException("Error finding all trip info by customer:\n" + ex.getMessage());
        }
    }

    @Override
    public List<TripInfo> findAllTripInfoByTrip(Trip trip) throws LogicException {
        try {
            LOGGER.info("TripInfoManager: Finding all trip info by trip from REST service (XML).");
            return webClient.findAllTripInfoByTrip(new GenericType<List<TripInfo>>() {}, trip.getId().toString());
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE, "TripInfoManager: Exception finding all trip info by trip, {0}", ex.getMessage());
            throw new LogicException("Error finding all trip info by trip:\n" + ex.getMessage());
        }
    }

    @Override
    public void updateTripInfo(TripInfo tripInfo) throws LogicException {
        try {
            LOGGER.info("TripInfoManager: Updating trip info from REST service (XML).");
            webClient.update(tripInfo);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE, "TripInfoManager: Exception updating trip info, {0}", ex.getMessage());
            throw new LogicException("Error updating trip info:\n" + ex.getMessage());
        }
    }

    @Override
    public void deleteTripInfo(TripInfo tripInfo) throws LogicException {
        try {
            LOGGER.info("TripInfoManager: Deleting trip info from REST service (XML).");
            webClient.delete(tripInfo.getTripInfoId().getCustomerId(), tripInfo.getTripInfoId().getTripId().toString());
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE, "TripInfoManager: Exception deleting trip info, {0}", ex.getMessage());
            throw new LogicException("Error deleting trip info:\n" + ex.getMessage());
        }
    }

    @Override
    public void createTripInfo(TripInfo tripInfo) throws LogicException {
        try {
            LOGGER.info("TripInfoManager: Creating trip info from REST service (XML).");
            webClient.create(tripInfo);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE, "TripInfoManager: Exception creating trip info, {0}", ex.getMessage());
            throw new LogicException("Error creating trip info:\n" + ex.getMessage());
        }
    }

    @Override
    public List<TripInfo> findActiveTripInfoByCustomer(Customer customer) throws LogicException {
        try {
            LOGGER.info("TripInfoManager: Finding active trip info by customer from REST service (XML).");
            return webClient.findActiveTripInfoByCustomer(new GenericType<List<TripInfo>>() {}, customer.getMail());
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE, "TripInfoManager: Exception finding active trip info by customer, {0}", ex.getMessage());
            throw new LogicException("Error finding active trip info by customer:\n" + ex.getMessage());
        }
    }

    @Override
    public List<TripInfo> findInactiveTripInfoByCustomer(Customer customer) throws LogicException {
        try {
            LOGGER.info("TripInfoManager: Finding inactive trip info by customer from REST service (XML).");
            return webClient.findInactiveTripInfoByCustomer(new GenericType<List<TripInfo>>() {}, customer.getMail());
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE, "TripInfoManager: Exception finding inactive trip info by customer, {0}", ex.getMessage());
            throw new LogicException("Error finding inactive trip info by customer:\n" + ex.getMessage());
        }
    }
}
