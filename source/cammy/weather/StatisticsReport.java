package cammy.weather;

import static java.lang.Math.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import cammy.weather.DistanceConverter.Distance;
import cammy.weather.TemperatureConverter.Temperature;
import com.beust.jcommander.Parameter;
import lombok.*;

/**
 *
 */
@RequiredArgsConstructor
public class StatisticsReport implements ObservationReader.ObservationListener {

	private final static Function<String, AtomicInteger> COMPUTE_ZERO = s -> new AtomicInteger();
	private Map<String, AtomicInteger> observationsByObservatory = new HashMap<>();

	private final Temperature expectedTemperature;
	private final Distance expectedDistance;

	private double sumOfTemperature, lastLocationX, lastLocationY;

	@Getter
	private double
		minimumTemperature = Double.MAX_VALUE,
		maximumTemperature = Double.MIN_VALUE,
		totalDistanceTravelled = 0;

	double meanTemperature(){
		return sumOfTemperature / totalOfObservations();
	}

	int totalOfObservations() {
		return observationsByObservatory.values().stream().mapToInt( AtomicInteger::get ).sum();
	}

	@Override
	public void consume(String timestamp, double locationX, double locationY, double temperature, String country) {
		memorizeTemperature( country, temperature );
		memorizeDistance( country, locationX, locationY );
		observationsByObservatory.computeIfAbsent( country, COMPUTE_ZERO ).incrementAndGet();
	}

	void memorizeTemperature( String country, double temperature ){
		val countryTempType = Observatory.getTemperatureTypeForCountry( country );
		val countryTemp = TemperatureConverter.convert( temperature, countryTempType, expectedTemperature );
		minimumTemperature = Math.min( minimumTemperature, countryTemp );
		maximumTemperature = Math.max( maximumTemperature, countryTemp );
		sumOfTemperature+= countryTemp;
	}

	void memorizeDistance( String country, double locationX, double locationY ) {
		val countryDistanceType = Observatory.getDistanceTypeForCountry( country );
		val countryLocationX = DistanceConverter.convert( locationX, countryDistanceType, expectedDistance );
		val countryLocationY = DistanceConverter.convert( locationY, countryDistanceType, expectedDistance );
		totalDistanceTravelled += euclideanDistance( lastLocationX, lastLocationY, countryLocationX, countryLocationY );
		lastLocationX = countryLocationX;
		lastLocationY = countryLocationY;
	}

	static double euclideanDistance( double x1, double y1, double x2, double y2 ) {
		return sqrt(
				pow( x2 - x1, 2 ) + pow( y2 - y1, 2 )
			);
	}

	Map<String, Object> generateReport( ReportParameters parameters ) {
		val report = new LinkedHashMap<String, Object>();

		if ( parameters.minimumTemperature )
			report.put( "minimum temperature", minimumTemperature() );
		if ( parameters.maximumTemperature )
			report.put( "maximum temperature", maximumTemperature() );
		if ( parameters.meanTemperature )
			report.put( "mean temperature", meanTemperature() );
		if ( parameters.totalDistanceTravelled )
			report.put( "total distance travelled", totalDistanceTravelled() );
		if ( parameters.observationsByObservatory )
			report.put( "number of observations from each observatory", observationsByObservatory.toString() );

		return report;
	}

	static class ReportParameters {

		@Parameter(names = "--minimum-temperature", description = "Includes the minimum temperature on the Statistical Report")
		boolean minimumTemperature;

		@Parameter(names = "--maximum-temperature", description = "Includes the maximum temperature on the Statistical Report")
		boolean maximumTemperature;

		@Parameter(names = "--mean-temperature", description = "Includes the mean temperature on the Statistical Report")
		boolean meanTemperature;

		@Parameter(names = "--observations-by-observatory", description = "Includes the number of observations from each observatory on the Statistical Report")
		boolean observationsByObservatory;

		@Parameter(names = "--total-distance-travelled", description = "Includes the total distance travelled on the Statistical Report")
		boolean totalDistanceTravelled;

		boolean containsAtLeastOneParameterSet(){
			return minimumTemperature || maximumTemperature || meanTemperature
				|| totalDistanceTravelled || observationsByObservatory;
		}
	}
}
