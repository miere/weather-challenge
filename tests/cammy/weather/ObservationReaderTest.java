package cammy.weather;

import static org.mockito.Mockito.*;
import cammy.weather.ObservationReader.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

/**
 *
 */
class ObservationReaderTest {

	static final String
		PRE_GENERATED = "tests-resources/pre-generated.data",
		PRE_GENERATED_WITH_FAILURES = "tests-resources/pre-generated-with-failures.data"
	;

	ObservationListener listener = mock( ObservationListener.class );
	FailureHandler failureHandler = spy( new FailureHandler( null ) );

	@BeforeEach
	@SneakyThrows
	void configureMocks(){
		doNothing().when( failureHandler ).memorizeBadObservation( anyString() );
	}

	@SneakyThrows
	@DisplayName( "Can read the pre-generated report as expected" )
	@Test void read1()
	{
		ObservationReader.read( PRE_GENERATED, listener, failureHandler );
		verify( failureHandler, never() ).memorizeBadObservation( anyString() );
		verify( listener, times( 2 ) ).consume( any(), anyDouble(), anyDouble(), anyDouble(), any() );
		verify( listener ).consume( eq("2018-06-17T01:42"), eq(59D), eq(3D), eq(5D), eq("AU") );
		verify( listener ).consume( eq("2018-06-17T01:42"), eq(122D), eq(63D), eq(42D), eq("US") );
	}

	@SneakyThrows
	@DisplayName( "Failures on the report is notified to the failure handler" )
	@Test void read2() {
		ObservationReader.read( PRE_GENERATED_WITH_FAILURES, listener, failureHandler );
		verify( failureHandler, times(3) ).memorizeBadObservation( anyString() );
		verify( failureHandler ).memorizeBadObservation( eq("2018-06-17T01:4222,53|42|AU") );
		verify( failureHandler ).memorizeBadObservation( eq("2018-06-17T01:42|123|42|US") );
		verify( failureHandler ).memorizeBadObservation( eq("2018-06-17T0") );
		verify( listener ).consume( eq("2018-06-17T01:42"), eq(59D), eq(3D), eq(5D), eq("BR") );
		verify( listener ).consume( eq("2018-06-17T01:42"), eq(122D), eq(53D), eq(42D), eq("FR") );
	}
}