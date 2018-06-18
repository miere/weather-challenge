package cammy.weather;

import java.io.*;
import java.util.Collections;
import cammy.weather.DistanceConverter.Distance;
import cammy.weather.ObservationReader.FailureHandler;
import cammy.weather.TemperatureConverter.Temperature;
import com.google.code.externalsorting.ExternalSort;
import lombok.*;
import org.junit.jupiter.api.*;

/**
 *
 */
@Disabled
class SandboxTest {

	@SneakyThrows
	@Test void generateAFile(){
		val size = 16*1024;
		try ( val file = new FileWriter( "output/mock.data" ) ) {
			try (Writer writer = new BufferedWriter( file, size ) ) {
				WeatherMockDataGenerator.create().generate( 100000000, writer );
			}
		}
	}

	@SneakyThrows @Test
	void readAFile3() {
		try ( val failureHandler = new FailureHandler( "output/mock.data.failures" ) ) {
			val report = new StatisticsReport( Temperature.CELSIUS, Distance.KM );
			ObservationReader.read( "output/mock.data", report, failureHandler );
			System.out.println( "Min Temp: " + report.minimumTemperature() );
			System.out.println( "Min Temp: " + report.maximumTemperature() );
			System.out.println( "Min Temp: " + report.meanTemperature() );
			System.out.println( "Total Dist: " + report.totalDistanceTravelled() );
			System.out.println( "Total Observations: " + report.totalOfObservations() );
		}
	}

	@SneakyThrows @Test
	void sortFile(){
		ExternalSort.mergeSortedFiles(
			Collections.singletonList( new File("output/mock.data") ),
			new File( "output/mock.sorted" ) );
	}
}
