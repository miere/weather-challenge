package cammy.weather;

import static cammy.weather.WeatherMockDataGenerator.MAX_DISTANCE_BETWEEN_METRICS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.io.StringWriter;
import java.time.*;
import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.api.*;

/**
 * Unit tests for the {@link WeatherMockDataGenerator} class.
 */
class WeatherMockDataGeneratorTest {

	@DisplayName( "The generated file contains the expected number of lines" )
	@Test void generate1(){
		val writer = new StringWriter();
		WeatherMockDataGenerator.create().generate( 10, writer );
		val lines = writer.toString().split( "\n" );
		assertEquals( 10, lines.length );
	}

	@DisplayName( "The generated file contains registers from all countries' observatories" )
	@Test void generate2(){
		val writer = new StringWriter();
		WeatherMockDataGenerator.create().generate( 10, writer );
		val lines = writer.toString().split( "\n" );
		assertEquals( 2, Stream.of( lines ).filter( l -> l.endsWith( "AU" ) ).count() );
		assertEquals( 2, Stream.of( lines ).filter( l -> l.endsWith( "US" ) ).count() );
		assertEquals( 2, Stream.of( lines ).filter( l -> l.endsWith( "FR" ) ).count() );
		assertEquals( 4, Stream.of( lines ).filter( l -> l.endsWith( "BR" ) ).count() );
	}

	@DisplayName( "The representation of new balloon position is correctly returned after the balloon move" )
	@Test void moveBalloon(){
		val dataGenerator = spy( WeatherMockDataGenerator.create() );
		doReturn( 5 ).when( dataGenerator ).simulateAMove();

		val balloon = BalloonLocation.create();
		val newPositionRepresentation = dataGenerator.moveBalloon( balloon );
		assertEquals( "5,5", newPositionRepresentation );
	}

	@DisplayName( "The 'move simulation' should return a value in a predictable range." )
	@Test void simulateAMove(){
		val dataGenerator = WeatherMockDataGenerator.create();
		val move = dataGenerator.simulateAMove();
		assertBetween( move, 0, MAX_DISTANCE_BETWEEN_METRICS );
	}

	@DisplayName( "The 'change of temperature simulation' should bound to the observatory's temperature range." )
	@Test void simulateAChangeOfTemperatureBasedOnObservatoryMetrics(){
		val dataGenerator = WeatherMockDataGenerator.create();

		val brazilObservatory = Observatory.create( "BR", 5, 35 );
		val brTemp = dataGenerator.simulateAChangeOfTemperatureBasedOnObservatoryMetrics( brazilObservatory );
		assertBetween( brTemp, 5, 35 );

		val antarcticaObservatory = Observatory.create( "AN", -125, 5 );
		val anTemp = dataGenerator.simulateAChangeOfTemperatureBasedOnObservatoryMetrics( antarcticaObservatory );
		assertBetween( anTemp, -125, 5 );
	}

	static void assertBetween( int actual, int minExpectedValue, int maxExpectedValue ) {
		assertTrue( actual >= minExpectedValue );
		assertTrue( actual < maxExpectedValue );
	}

	@DisplayName( "Can format date on the expected date format" )
	@Test void formatTimestamp(){
		val timestamp = LocalDateTime.of( 1985, Month.FEBRUARY, 28, 7, 29, 0 );
		val dataGenerator = WeatherMockDataGenerator.create();
		val formatted = dataGenerator.formatTimestamp( timestamp );
		assertEquals( "1985-02-28T07:29", formatted );
	}
}