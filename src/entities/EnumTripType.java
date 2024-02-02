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

	//constructor with swicht to return from index
	public static EnumTripType getEnumTripType(int x) {
		switch (x) {
			case 1:
				return CULTURE;
			case 2:
				return NATURE;
			case 3:
				return LEISURE;
			case 4:
				return SPORTS;
		}
		return null;
	}
}
