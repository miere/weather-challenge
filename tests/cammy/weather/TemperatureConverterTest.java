package cammy.weather;

import static cammy.weather.TemperatureConverter.Temperature.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;

/**
 *
 */
class TemperatureConverterTest {

	@Test @DisplayName( "Can convert celsius to fahrenheit" )
	void convert1() {
		assertEquals( 32, TemperatureConverter.convert( 0, CELSIUS, FAHRENHEIT ), 0.01 );
		assertEquals( 33.8, TemperatureConverter.convert( 1, CELSIUS, FAHRENHEIT ), 0.01 );
		assertEquals( 35.6, TemperatureConverter.convert( 2, CELSIUS, FAHRENHEIT ), 0.01 );
		assertEquals( 37.4, TemperatureConverter.convert( 3, CELSIUS, FAHRENHEIT ), 0.01 );
	}

	@Test @DisplayName( "Can convert celsius to kelvin" )
	void convert2() {
		assertEquals( 273.15, TemperatureConverter.convert( 0, CELSIUS, KELVIN ) );
		assertEquals( 274.15, TemperatureConverter.convert( 1, CELSIUS, KELVIN ) );
		assertEquals( 275.15, TemperatureConverter.convert( 2, CELSIUS, KELVIN ) );
		assertEquals( 276.15, TemperatureConverter.convert( 3, CELSIUS, KELVIN ) );
	}

	@Test @DisplayName( "Can convert kelvin to fahrenheit" )
	void convert3() {
		assertEquals( 32, TemperatureConverter.convert( 273.15, KELVIN, FAHRENHEIT ), 0.01 );
		assertEquals( 33.8, TemperatureConverter.convert( 274.15, KELVIN, FAHRENHEIT ), 0.01 );
		assertEquals( 35.6, TemperatureConverter.convert( 275.15, KELVIN, FAHRENHEIT ), 0.01 );
		assertEquals( 37.4, TemperatureConverter.convert( 276.15, KELVIN, FAHRENHEIT ), 0.01 );
	}

	@Test @DisplayName( "Can convert kelvin to celsius" )
	void convert4() {
		assertEquals( 0, TemperatureConverter.convert( 273.15, KELVIN, CELSIUS ) );
		assertEquals( 1, TemperatureConverter.convert( 274.15, KELVIN, CELSIUS ) );
		assertEquals( 2, TemperatureConverter.convert( 275.15, KELVIN, CELSIUS ) );
		assertEquals( 3, TemperatureConverter.convert( 276.15, KELVIN, CELSIUS ) );
	}

	@Test @DisplayName( "Can convert fahrenheit to kelvin" )
	void convert5() {
		assertEquals( 273.15, TemperatureConverter.convert( 32, FAHRENHEIT, KELVIN ), 0.01 );
		assertEquals( 274.15, TemperatureConverter.convert( 33.8, FAHRENHEIT, KELVIN ), 0.01 );
		assertEquals( 275.15, TemperatureConverter.convert( 35.6, FAHRENHEIT, KELVIN ), 0.01 );
		assertEquals( 276.15, TemperatureConverter.convert( 37.4, FAHRENHEIT, KELVIN ), 0.01 );
	}

	@Test @DisplayName( "Can convert fahrenheit to celsius" )
	void convert6() {
		assertEquals( 0, TemperatureConverter.convert( 32, FAHRENHEIT, CELSIUS ), 0.01 );
		assertEquals( 1, TemperatureConverter.convert( 33.8, FAHRENHEIT, CELSIUS ), 0.01 );
		assertEquals( 2, TemperatureConverter.convert( 35.6, FAHRENHEIT, CELSIUS ), 0.01 );
		assertEquals( 3, TemperatureConverter.convert( 37.4, FAHRENHEIT, CELSIUS ), 0.01 );
	}
}