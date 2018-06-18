package cammy.weather;

import cammy.weather.DistanceConverter.Distance;
import cammy.weather.TemperatureConverter.Temperature;
import lombok.*;

/**
 * Represents the observatory. Contains data regarding its location and
 * its temperature and distance types.
 */
@Value
@RequiredArgsConstructor
class Observatory {

	final String country;
	final Temperature temp;
	final Distance dist;
	final int initialTempRange, finalTempRange;

	static Observatory create(String country, int initialTempRange, int finalTempRange) {
		return new Observatory( country,
			getTemperatureTypeForCountry( country ),
			getDistanceTypeForCountry( country ),
			initialTempRange, finalTempRange
		);
	}

	static Temperature getTemperatureTypeForCountry(String country) {
		switch ( country ){
			case "AU": return Temperature.CELSIUS;
			case "US": return Temperature.FAHRENHEIT;
			default: return Temperature.KELVIN;
		}
	}

	static Distance getDistanceTypeForCountry(String country) {
		switch ( country ) {
			case "US": return Distance.MILES;
			case "FR": return Distance.M;
			default: return Distance.KM;
		}
	}
}