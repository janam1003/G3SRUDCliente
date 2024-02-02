/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import implementations.TripInfoManagerImplementation;
import interfaces.TripInfoManager;
import java.util.logging.Logger;

/**
 *
 * @author 2dam
 */
public class TripInfoManagerFactory {

	// Static variable to store the TripInfoManager instance
	private static TripInfoManager tripInfoManager;

	/**
	 * Logger object used to log messages for the application.
	 */
	private static final Logger LOGGER = Logger.getLogger(TripInfoManagerFactory.class.getName());

	/**
	 * Static method to get the TripInfoManager instance. If an instance doesnt exist
	 * create one.
	 * 
	 * @return TripInfoManager instance
	 */
	public static TripInfoManager getTripInfoManager() {
		// Logger
		LOGGER.info("Initializing TripManagerFactory.");

		// Check if the tripInfoManager instance is not previously loaded.
		if (tripInfoManager == null) {

			/**
			 * If not loaded, create a new instance of TripInfoManager using the
			 * TripInfoManagerImplementation class.
			 */
			tripInfoManager = (TripInfoManager) new TripInfoManagerImplementation();

		}

		// Return the TripInfoManager instance.
		return tripInfoManager;
	}

}
