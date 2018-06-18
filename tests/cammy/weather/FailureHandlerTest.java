package cammy.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.file.*;
import cammy.weather.ObservationReader.FailureHandler;
import lombok.*;
import org.junit.jupiter.api.*;

/**
 *
 */
class FailureHandlerTest {

	@SneakyThrows
	@DisplayName( "Can memorize bad entry and save into a file" )
	@Test void memorizeBadObservation1()
	{
		try (val handler = new FailureHandler( "output/failure.report" )){
			handler.memorizeBadObservation( "BADENTRY" );
		}

		val bytes = Files.readAllBytes( Paths.get("output/failure.report") );
		val reportContent = new String( bytes );
		assertEquals( "BADENTRY\n", reportContent );
	}

	@SneakyThrows
	@DisplayName( "Can memorize two bad entries and save into a file" )
	@Test void memorizeBadObservation2()
	{
		try (val handler = new FailureHandler( "output/failure.report" )){
			handler.memorizeBadObservation( "FIRST" );
			handler.memorizeBadObservation( "SECOND" );
		}

		val bytes = Files.readAllBytes( Paths.get("output/failure.report") );
		val reportContent = new String( bytes );
		assertEquals( "FIRST\nSECOND\n", reportContent );
	}
}
