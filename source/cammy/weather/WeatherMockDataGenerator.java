package cammy.weather;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import lombok.*;

/**
 * Generates a simple and ordered set of observations and store it into a file.
 */
@RequiredArgsConstructor
class WeatherMockDataGenerator {

	static final Observatory
		AU = Observatory.create( "AU", 0, 43 ),
		US = Observatory.create( "US", -10, 35 ),
		FR = Observatory.create( "FR", -5, 25 ),
		BR = Observatory.create( "BR", 0, 35 )
	;

	static final int MAX_DISTANCE_BETWEEN_METRICS = 100;
	static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern( "yyyy-MM-dd'T'hh:mm" );

	final Queue<Observatory> observatories;

	void generate( long numOfRegisters, Writer output ) {
		val numOfRegsPerObservatory = numOfRegisters / observatories.size();
		val extraRegsForLastObservatory = numOfRegisters % observatories.size();
		val balloon = BalloonLocation.create();

		Observatory observatory = null;
		while ( !observatories.isEmpty() ){
			observatory = observatories.poll();
			generateRegisterBasedOnCountryIndicator( output, balloon, observatory, numOfRegsPerObservatory );
		}

		if ( extraRegsForLastObservatory > 0 )
			generateRegisterBasedOnCountryIndicator( output, balloon, observatory, extraRegsForLastObservatory );
	}

	void generateRegisterBasedOnCountryIndicator( Writer output, BalloonLocation balloon, Observatory observatory, long numOfRegs )
	{
		for ( long i = 0; i < numOfRegs; i++ ) {
			val location = moveBalloon( balloon );
			val temp = simulateAChangeOfTemperatureBasedOnObservatoryMetrics( observatory );
			writeToOutput( output, location, temp, observatory.country() );
		}
	}

	void writeToOutput( Writer output, String location, int temp, String country ) {
		try {
			val timestamp = formatTimestamp( LocalDateTime.now() );
			output.append( timestamp ).append( '|' )
				  .append( location ).append( '|' )
				  .append( String.valueOf( temp ) ).append( '|' )
				  .append( country ).append( '\n' );
		} catch ( IOException cause ) {
			throw new IllegalStateException( cause );
		}
	}

	String moveBalloon( BalloonLocation balloon ){
		val gapX = simulateAMove();
		val gapY = simulateAMove();
		balloon.moveBy( gapX, gapY );
		return balloon.toString();
	}

	int simulateAMove(){
		return ThreadLocalRandom.current().nextInt( MAX_DISTANCE_BETWEEN_METRICS );
	}

	int simulateAChangeOfTemperatureBasedOnObservatoryMetrics( Observatory observatory ) {
		return ThreadLocalRandom.current().nextInt( observatory.initialTempRange(), observatory.finalTempRange() );
	}

	String formatTimestamp(LocalDateTime localDateTime) {
		return localDateTime.format( FORMATTER );
	}

	public static WeatherMockDataGenerator create(){
		return new WeatherMockDataGenerator(
			new ArrayDeque<>( Arrays.asList( AU, US, FR, BR ) )
		);
	}
}