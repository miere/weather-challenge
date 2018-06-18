package cammy.weather;

import static cammy.weather.DistanceConverter.Distance.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;

/**
 *
 */
class DistanceConverterTest {

	@DisplayName( "Can convert Meter to Mile" )
	@Test void convert1(){
		assertEquals( 0.000621371, DistanceConverter.convert( 1, M, MILES ), 0.00001 );
		assertEquals( 0.00124274, DistanceConverter.convert( 2, M, MILES ), 0.000001 );
	}

	@DisplayName( "Can convert Meter to Kilometer" )
	@Test void convert2(){
		assertEquals( 0.001, DistanceConverter.convert( 1, M, KM ), 0.00001 );
		assertEquals( 0.002, DistanceConverter.convert( 2, M, KM ), 0.00001 );
	}

	@DisplayName( "Can convert Mile to Meter" )
	@Test void convert3(){
		assertEquals( 1609.344, DistanceConverter.convert( 1, MILES, M ), 0.00001 );
		assertEquals( 3218.688, DistanceConverter.convert( 2, MILES, M ), 0.00001 );
	}

	@DisplayName( "Can convert Mile to Kilometer" )
	@Test void convert4(){
		assertEquals( 1.609344, DistanceConverter.convert( 1, MILES, KM ), 0.00001 );
		assertEquals( 3.218688, DistanceConverter.convert( 2, MILES, KM ), 0.00001 );
	}

	@DisplayName( "Can convert Kilometer to Meter" )
	@Test void convert5(){
		assertEquals( 1000, DistanceConverter.convert( 1, KM, M ), 0.00001 );
		assertEquals( 2000, DistanceConverter.convert( 2, KM, M ), 0.00001 );
	}

	@DisplayName( "Can convert Kilometer to MILES" )
	@Test void convert6(){
		assertEquals( 1.86411, DistanceConverter.convert( 3, KM, MILES ), 0.00001 );
		assertEquals( 2.48548, DistanceConverter.convert( 4, KM, MILES ), 0.00001 );
	}
}