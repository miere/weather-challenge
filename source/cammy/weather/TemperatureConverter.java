package cammy.weather;

import lombok.experimental.UtilityClass;

/**
 *
 */
@UtilityClass
public class TemperatureConverter {

	double convert( double value, Temperature from, Temperature to ){
		switch ( from ){
			case KELVIN: return convertKevin( value, to );
			case CELSIUS: return convertCelsius( value, to );
			default: return convertFahrenheit( value, to );
		}
	}

	private double convertKevin( double value, Temperature to ) {
		switch ( to ) {
			case CELSIUS: return value - 273.15;
			case FAHRENHEIT: return value * 9.0/5.0 - 459.67;
			default: return value;
		}
	}

	private double convertCelsius( double value, Temperature to ) {
		switch ( to ) {
			case KELVIN: return value + 273.15;
			case FAHRENHEIT: return value * 9.0/5.0 + 32;
			default: return value;
		}
	}

	private double convertFahrenheit( double value, Temperature to ) {
		switch ( to ) {
			case KELVIN: return ( value + 459.67 ) * 5.0/9.0;
			case CELSIUS: return ( value - 32 ) * 5.0/9.0;
			default: return value;
		}
	}

	enum Temperature {
		CELSIUS, FAHRENHEIT, KELVIN
	}
}
