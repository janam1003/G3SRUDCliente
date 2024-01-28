package factories;

import java.util.logging.Logger;
import implementations.TripManagerImplementation;
import interfaces.TripManager;

public class TripManagerFactory {

	// Static variable to store the TripManager instance
	private static TripManager tripManager;

	/**
	 * Logger object used to log messages for the application.
	 */
	private static final Logger LOGGER = Logger.getLogger(TripManagerFactory.class.getName());

	/**
	 * Static method to get the TripManager instance. If an instance doesnt exist
	 * create one.
	 * 
	 * @return TripManager instance
	 */
	public static TripManager getTripManager() {
		// Logger
		LOGGER.info("Initializing TripManagerFactory.");

		// Check if the tripManager instance is not previously loaded.
		if (tripManager == null) {

			/**
			 * If not loaded, create a new instance of TripManager using the
			 * TripManagerImplementation class.
			 */
			tripManager = new TripManagerImplementation();

		}

		// Return the TripManager instance.
		return tripManager;
	}

}
