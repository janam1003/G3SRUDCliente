package entities;

/**
 *
 * @author Iñigo
 */
public enum EnumTripType {
    CULTURE,
    NATURE,
    LEISURE,
    SPORTS;

	//Constructor with switch
	public static EnumTripType getEnumTripType(int i) {
		switch (i) {
			case 1:
				return CULTURE;
			case 2:
				return NATURE;
			case 3:
				return LEISURE;
			case 4:
				return SPORTS;
			default:
				return null;
		}
	}
}
