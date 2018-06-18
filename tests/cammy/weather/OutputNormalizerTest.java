package cammy.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.StringWriter;
import cammy.weather.DistanceConverter.Distance;
import cammy.weather.TemperatureConverter.Temperature;
import lombok.val;
import org.junit.jupiter.api.*;

/**
 *
 */
class OutputNormalizerTest {

	static final String FIXED_TIMESTAMP = "2018-06-17T10:05";

	@Test @DisplayName( "Can normalize a FR entry into an AU one" )
	void consume1() {
		val writer = new StringWriter();
		val normalizer = new OutputNormalizer( Temperature.CELSIUS, Distance.KM, writer );
		normalizer.consume( FIXED_TIMESTAMP, 3000, 4000, 296.65, "FR" );
		val normalized = writer.toString();
		assertEquals( "2018-06-17T10:05|3,4|23.5|FR\n", normalized );
	}

	@Test @DisplayName( "Can normalize a BR entry into an AU one" )
	void consume2() {
		val writer = new StringWriter();
		val normalizer = new OutputNormalizer( Temperature.CELSIUS, Distance.KM, writer );
		normalizer.consume( FIXED_TIMESTAMP, 3, 4, 296.65, "BR" );
		val normalized = writer.toString();
		assertEquals( "2018-06-17T10:05|3,4|23.5|BR\n", normalized );
	}

	@Test @DisplayName( "Can normalize a US entry into an AU one" )
	void consume3() {
		val writer = new StringWriter();
		val normalizer = new OutputNormalizer( Temperature.CELSIUS, Distance.KM, writer );
		normalizer.consume( FIXED_TIMESTAMP, 3, 4, 33.8, "US" );
		val normalized = writer.toString();
		assertEquals( "2018-06-17T10:05|4.828,6.437|1|US\n", normalized );
	}
}