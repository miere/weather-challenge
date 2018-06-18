package cammy.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Map;
import cammy.weather.DistanceConverter.Distance;
import cammy.weather.StatisticsReport.ReportParameters;
import cammy.weather.TemperatureConverter.Temperature;
import lombok.val;
import org.junit.jupiter.api.*;

/**
 *
 */
class StatisticsReportTest {

	static final String FIXED_TIMESTAMP = "2018-06-17T10:05";

	@Test @DisplayName( "Can calculate the Euclidean Distance between A(x1,y1) and B(x2,y2)" )
	void euclideanDistance() {
		val distance = StatisticsReport.euclideanDistance( 3, 3, 7, 6 );
		assertEquals( 5, distance );
	}

	@Test @DisplayName( "The Euclidean Distance between A(x1,y1) and B(x2,y2) is same between B(x2,y2) and A(x1,y1)" )
	void euclideanDistance1() {
		val distance = StatisticsReport.euclideanDistance( -1.5, -2, 1.5, 2 );
		assertEquals( 5, distance );
		val distance2 = StatisticsReport.euclideanDistance( 1.5, 2, -1.5, -2 );
		assertEquals( 5, distance2 );
	}

	@Test @DisplayName( "Can memorize a Fahrenheit temperature on a Celsius report" )
	void memorizeTemperature1() {
		val report = new StatisticsReport( Temperature.CELSIUS, Distance.KM );
		val fahrenheitUsTemp = 33.8;
		report.memorizeTemperature( "US", fahrenheitUsTemp );
		assertEquals( 1, report.maximumTemperature(), 0.00001 );
		assertEquals( 1, report.minimumTemperature(), 0.00001 );
	}

	@Test @DisplayName( "Can memorize a Celsius temperature on a Fahrenheit report" )
	void memorizeTemperature2() {
		val report = new StatisticsReport( Temperature.FAHRENHEIT, Distance.KM );
		val celsiusAuTemp = 1;
		report.memorizeTemperature( "AU", celsiusAuTemp );
		assertEquals( 33.8, report.maximumTemperature(), 0.00001 );
		assertEquals( 33.8, report.minimumTemperature(), 0.00001 );
	}

	@Test @DisplayName( "Can memorize a KM distance on a MILES report" )
	void memorizeDistance1() {
		val report = new StatisticsReport( Temperature.FAHRENHEIT, Distance.MILES );
		report.memorizeDistance( "AU", 3, 4 );
		assertEquals( 3.10686, report.totalDistanceTravelled(), 0.00001 );
	}

	@Test @DisplayName( "Can memorize a MILES distance on a Meter report" )
	void memorizeDistance2() {
		val report = new StatisticsReport( Temperature.FAHRENHEIT, Distance.M );
		report.memorizeDistance( "US", 3, 4 );
		assertEquals( 8046.72, report.totalDistanceTravelled(), 0.00001 );
	}

	@Test @DisplayName( "Report should include 'mean temperature'" )
	void meanOfTemperature() {
		val params = new ReportParameters();
		params.meanTemperature = true;
		val report = runReport( Temperature.CELSIUS, Distance.KM, params );
		assertEquals( 17.5, (double)report.get( "mean temperature" ), 0.00001 );
	}

	@Test @DisplayName( "Report should include 'minimum temperature'" )
	void minimumTemperature() {
		val params = new ReportParameters();
		params.minimumTemperature = true;
		val report = runReport( Temperature.CELSIUS, Distance.KM, params );
		assertEquals( 15, (double)report.get( "minimum temperature" ) );
	}

	@Test @DisplayName( "Report should include 'maximum temperature'" )
	void maximumTemperature() {
		val params = new ReportParameters();
		params.maximumTemperature = true;
		val report = runReport( Temperature.CELSIUS, Distance.KM, params );
		assertEquals( 20, (double)report.get( "maximum temperature" ) );
	}

	@Test @DisplayName( "Report should include 'total distance travelled'" )
	void totalDistanceTravelled() {
		val params = new ReportParameters();
		params.totalDistanceTravelled = true;
		val report = runReport( Temperature.CELSIUS, Distance.KM, params );
		assertEquals( 15, (double)report.get( "total distance travelled" ) );
	}

	@Test @DisplayName( "Report should include 'number of observations from each observatory'" )
	void observationsByObservatory() {
		val params = new ReportParameters();
		params.observationsByObservatory = true;
		val report = runReport( Temperature.CELSIUS, Distance.KM, params );
		assertEquals( "{BR=1, AU=1, FR=1}", report.get( "number of observations from each observatory" ) );
	}

	Map<String, Object> runReport(Temperature temperature, Distance distance, ReportParameters parameters ){
		val report = new StatisticsReport( temperature, distance );
		report.consume( FIXED_TIMESTAMP, 3, 4, 15, "AU" );
		report.consume( FIXED_TIMESTAMP, 7, 7, 290.65, "BR" );
		report.consume( FIXED_TIMESTAMP, 4000, 3000, 293.15, "FR" );
		return report.generateReport( parameters );
	}
}