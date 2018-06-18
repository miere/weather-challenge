package cammy.weather;

import java.io.*;
import java.util.*;
import lombok.*;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * The observation file reader.
 */
@UtilityClass @Slf4j
class ObservationReader {

	/**
	 * Reads the observation file line by line. For each found line the
	 * {@code listener} is notified.
	 *
	 * @param fileName
	 * @param listener
	 * @throws IOException
	 */
	void read( String fileName, ObservationListener listener, FailureHandler failureHandler ) throws IOException {
		try (val inputStream = new FileInputStream(fileName )) {
			try (val sc = new Scanner(inputStream, "UTF-8")) {
				while ( sc.hasNextLine() ) {
					tokenizeLinesAndNotifyListener( sc.nextLine(), listener, failureHandler );
				}
				if ( sc.ioException() != null )
					throw new RuntimeException( sc.ioException() );
			}
		}
	}

	private void tokenizeLinesAndNotifyListener(String bytes, ObservationListener listener, FailureHandler failureHandler) throws IOException
	{
		try {
			val lineTokenizer = new StringTokenizer( bytes, "|" );
			val timestamp = lineTokenizer.nextToken();
			val location = lineTokenizer.nextToken().split( "," );
			val temperature = lineTokenizer.nextToken();
			val country = lineTokenizer.nextToken();
			listener.consume( timestamp, Double.valueOf( location[0] ),
					Double.valueOf( location[1] ),
					Double.valueOf( temperature ), country );
		} catch ( Throwable cause ) {
			log.debug( "Failed to parse line: " + bytes, cause );
			failureHandler.memorizeBadObservation( bytes );
		}
	}

	/**
	 * A listener of observations found on log files.
	 */
	interface ObservationListener {

		void consume(String timestamp, double locationX, double locationY, double temperature, String country);
	}

	/**
	 * Handle failures during the reading process, holding the bad observations
	 * and storing it into a file for further use.
	 */
	@RequiredArgsConstructor
	class FailureHandler implements Closeable {

		final String fileName;

		@Getter
		private File outputFile;

		@Getter(lazy = true, value = AccessLevel.PRIVATE)
		private final Writer outputFileWriter = createTempFileWriter();

		private Writer createTempFileWriter() {
			try {
				if ( outputFile == null )
					outputFile = new File( fileName );
				return new FileWriter( outputFile );
			}
			catch ( IOException e ) { throw new IllegalStateException( e ); }
		}

		void memorizeBadObservation( String observation ) throws IOException {
			outputFileWriter().append( observation ).append( "\n" );
		}

		@Override
		public void close() throws IOException {
			if ( outputFile() != null ) {
				outputFileWriter().close();
			}
		}
	}
}
