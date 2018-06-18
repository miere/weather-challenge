package cammy.weather;

import lombok.*;

/**
 * A mutable representation of the balloon's current location.
 */
@NoArgsConstructor( staticName = "create" )
@AllArgsConstructor( staticName = "at")
class BalloonLocation {

	@Getter
	private long x, y;

	void moveBy( long deltaX, long deltaY ){
		this.x += deltaX;
		this.y += deltaY;
	}

	@Override
	public String toString() {
		return x + "," + y;
	}
}
