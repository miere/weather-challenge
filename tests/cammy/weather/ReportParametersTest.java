package cammy.weather;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import cammy.weather.StatisticsReport.ReportParameters;
import lombok.val;
import org.junit.jupiter.api.*;

/**
 *
 */
class ReportParametersTest {

	@DisplayName( "Can identify when no report parameter was informed" )
	@Test void containsAtLeastOneParameterSet(){
		val params = new ReportParameters();
		assertFalse( params.containsAtLeastOneParameterSet() );
	}

	@DisplayName( "Can identify when at least 'observationsByObservatory' parameter was informed" )
	@Test void containsAtLeastOneParameterSet1(){
		val params = new ReportParameters();
		params.observationsByObservatory = true;
		assertTrue( params.containsAtLeastOneParameterSet() );
	}

	@DisplayName( "Can identify when at least 'totalDistanceTravelled' parameter was informed" )
	@Test void containsAtLeastOneParameterSet2(){
		val params = new ReportParameters();
		params.totalDistanceTravelled = true;
		assertTrue( params.containsAtLeastOneParameterSet() );
	}

	@DisplayName( "Can identify when at least 'minimumTemperature' parameter was informed" )
	@Test void containsAtLeastOneParameterSet3(){
		val params = new ReportParameters();
		params.minimumTemperature = true;
		assertTrue( params.containsAtLeastOneParameterSet() );
	}

	@DisplayName( "Can identify when at least 'maximumTemperature' parameter was informed" )
	@Test void containsAtLeastOneParameterSet4(){
		val params = new ReportParameters();
		params.maximumTemperature = true;
		assertTrue( params.containsAtLeastOneParameterSet() );
	}

	@DisplayName( "Can identify when at least 'meanTemperature' parameter was informed" )
	@Test void containsAtLeastOneParameterSet5(){
		val params = new ReportParameters();
		params.meanTemperature = true;
		assertTrue( params.containsAtLeastOneParameterSet() );
	}
}
