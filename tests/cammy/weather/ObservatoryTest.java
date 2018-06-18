package cammy.weather;

import static cammy.weather.DistanceConverter.Distance.*;
import static cammy.weather.TemperatureConverter.Temperature.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 *
 */
class ObservatoryTest {

	@Test @DisplayName( "Australia should use Celsius for temperature" )
	void getTemperatureTypeForCountry1() {
		assertEquals( CELSIUS, Observatory.getTemperatureTypeForCountry( "AU" ) );
	}

	@Test @DisplayName( "USA should use Fahrenheit for temperature" )
	void getTemperatureTypeForCountry2() {
		assertEquals( FAHRENHEIT, Observatory.getTemperatureTypeForCountry( "US" ) );
	}

	@Test @DisplayName( "France should use Kelvin for temperature" )
	void getTemperatureTypeForCountry3() {
		assertEquals( KELVIN, Observatory.getTemperatureTypeForCountry( "FR" ) );
	}

	@Test @DisplayName( "Other countries should use Kelvin for temperature" )
	void getTemperatureTypeForCountry4() {
		assertEquals( KELVIN, Observatory.getTemperatureTypeForCountry( "BR" ) );
	}

	@Test @DisplayName( "Australia should use KM for distance" )
	void getDistanceTypeForCountry1() {
		assertEquals( KM, Observatory.getDistanceTypeForCountry( "AU" ) );
	}

	@Test @DisplayName( "USA should use MILES for distance" )
	void getDistanceTypeForCountry2() {
		assertEquals( MILES, Observatory.getDistanceTypeForCountry( "US" ) );
	}

	@Test @DisplayName( "France should use M for distance" )
	void getDistanceTypeForCountry3() {
		assertEquals( M, Observatory.getDistanceTypeForCountry( "FR" ) );
	}

	@Test @DisplayName( "Other countries should use KM for distance" )
	void getDistanceTypeForCountry4() {
		assertEquals( KM, Observatory.getDistanceTypeForCountry( "BR" ) );
	}
}