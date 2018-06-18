package cammy.weather;

import lombok.experimental.UtilityClass;

/**
 *
 */
@UtilityClass
class DistanceConverter {

	double convert(double value, Distance from, Distance to) {
		switch ( from ){
			case KM: return convertKilometer( value, to );
			case M: return convertMeter( value, to );
			default: return convertMiles( value, to );
		}
	}

	private double convertMeter( double value, Distance to ) {
		switch ( to ) {
			case KM: return value / 1000;
			case MILES: return value * 0.000621371;
			default: return value;
		}
	}

	private double convertKilometer( double value, Distance to ) {
		switch ( to ) {
			case M: return value * 1000;
			case MILES: return value * 0.621371;
			default: return value;
		}
	}

	private double convertMiles( double value, Distance to ) {
		switch ( to ) {
			case M: return value * 1609.344;
			case KM: return value * 1.609344;
			default: return value;
		}
	}

	enum Distance {
		KM, M, MILES
	}
}
