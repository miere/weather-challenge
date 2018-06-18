package cammy.weather;

import java.io.*;
import java.text.*;
import java.util.Locale;
import cammy.weather.DistanceConverter.Distance;
import cammy.weather.TemperatureConverter.Temperature;
import lombok.*;

/**
 * Normalizes the observation output. It was designed to be simple yet memory-efficient
 * using streaming-based design.
 */
@RequiredArgsConstructor
public class OutputNormalizer implements ObservationReader.ObservationListener {

	private final NumberFormat format = new DecimalFormat( "#.###", DecimalFormatSymbols.getInstance( Locale.ENGLISH ) );

	private final Temperature expectedTemperature;
	private final Distance expectedDistance;
	private final Writer output;

	@Override
	public void consume(String timestamp, double locationX, double locationY, double temperature, String country) {
		val countryTempType = Observatory.getTemperatureTypeForCountry( country );
		val countryDistanceType = Observatory.getDistanceTypeForCountry( country );

		write( timestamp, country,
			DistanceConverter.convert( locationX, countryDistanceType, expectedDistance ),
			DistanceConverter.convert( locationY, countryDistanceType, expectedDistance ),
			TemperatureConverter.convert( temperature, countryTempType, expectedTemperature ) );
	}

	private void write(String timestamp, String country, double locationX, double locationY, double temperature) {
		try {
			output.append( timestamp ).append( '|' )
				.append( asString( locationX ) ).append( ',' ).append( asString( locationY )  ).append( '|' )
				.append( asString( temperature ) ).append( '|' )
				.append( country ).append( '\n' );
		} catch ( IOException cause ) {
			throw new IllegalStateException( cause );
		}
	}

	private String asString( double value ) {
		return format.format( value );
	}
}
