package cammy.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import lombok.val;
import org.junit.jupiter.api.*;

/**
 * Unit tests for the {@link BalloonLocation} class.
 */
class BalloonLocationTest {

	@Test @DisplayName( "Will set coordinates to 0 when create a BalloonLocation" )
	void create1() {
		val balloon = BalloonLocation.create();
		assertEquals( 0, balloon.x() );
		assertEquals( 0, balloon.y() );
	}

	@Test @DisplayName( "Won't move a balloon if passed 0 as gap" )
	void moveBy1() {
		val balloon = BalloonLocation.at( 1 ,2 );
		balloon.moveBy( 0, 0 );
		assertEquals( 1, balloon.x() );
		assertEquals( 2, balloon.y() );
	}

	@Test @DisplayName( "Can move a balloon by the respective new coordinate gaps" )
	void moveBy2() {
		val balloon = BalloonLocation.at( 1 ,2 );
		balloon.moveBy( 5, 2 );
		assertEquals( 6, balloon.x() );
		assertEquals( 4, balloon.y() );
	}

	@Test @DisplayName( "String representation is returned as expected" )
 	void toString1() {
		assertEquals( "0,0", BalloonLocation.create().toString() );
	}
}