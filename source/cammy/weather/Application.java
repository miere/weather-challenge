package cammy.weather;

import java.io.*;
import java.util.Collections;
import cammy.weather.DistanceConverter.Distance;
import cammy.weather.ObservationReader.FailureHandler;
import cammy.weather.StatisticsReport.ReportParameters;
import cammy.weather.TemperatureConverter.Temperature;
import com.beust.jcommander.*;
import com.google.code.externalsorting.ExternalSort;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * The Command Line Application.
 */
@Slf4j
public class Application {

	@Parameter( description = "<GENERATE|STATS|NORMALIZE>")
	String action;

	@Parameter( names = "-i", description = "Input file name.")
	String inputFileName;

	@Parameter( names = "-o", description = "Output file name.")
	String outputFileName;

	@Parameter( names = "-d", description = "Distance unit. Required for actions STATS and NORMALIZE.")
	Distance distance;

	@Parameter( names = "-t", description = "Temperature unit. Required for actions STATS and NORMALIZE.")
	Temperature temperature;

	@Parameter( names = "-n", description = "Number of observations to be generated. Required for action GENERATE.")
	long numOfObservations = 1000000;

	ReportParameters reportParameters = new ReportParameters();

	public static void main( String[] args ) throws IOException {
		val app = new Application();
		val cli = JCommander.newBuilder().programName( "<challenge.jar>" )
				.addObject( app ).addObject( app.reportParameters )
				.columnSize( 120 ).build();

		try {
			cli.parse( args );
			app.run();
		} catch ( IllegalArgumentException cause ) {
			log.warn( cause.getMessage() );
			System.out.println();
			cli.usage();
		}
	}

	private void run() throws IOException {
		if ( action == null || action.isEmpty() )
			throw new IllegalArgumentException( "No valid action defined." );

		switch ( Action.valueOf( action ) ) {
			case NORMALIZE: runNormalizer(); break;
			case STATS: runStatsReport(); break;
			case GENERATE: runGenerator(); break;
			default: throw new IllegalArgumentException( "No valid action defined." );
		}
	}

	private void runNormalizer() throws IOException {
		distanceIsRequired();
		temperatureIsRequired();
		inputFileIsRequired();
		outputFileIsRequired();

		log.info( "Normalizing report..." );
		val sortedFileName = sortFile( inputFileName );

		try ( val output = new FileWriter( outputFileName ) ) {
			try ( val failureHandler = new FailureHandler( inputFileName + ".failures" ) ) {
				val normalizer = new OutputNormalizer( temperature, distance, output );
				ObservationReader.read( sortedFileName, normalizer, failureHandler );
				log.info( "Normalized file saved as " + outputFileName );
				printFailureHandlerReport( failureHandler );
			}
		}
	}

	private void runStatsReport() throws IOException {
		distanceIsRequired();
		temperatureIsRequired();
		inputFileIsRequired();

		if ( !reportParameters.containsAtLeastOneParameterSet() )
			throw new IllegalArgumentException( "No report field was included." );

		log.info( "Generating stats report..." );
		val sortedFileName = sortFile( inputFileName );

		try ( val failureHandler = new FailureHandler( inputFileName + ".failures" ) ) {
			val report = new StatisticsReport( temperature, distance );
			ObservationReader.read( sortedFileName, report, failureHandler );
			printStatsReport( report );
			printFailureHandlerReport( failureHandler );
		}
	}

	private void printStatsReport(StatisticsReport report) {
		log.info( "Statistical Report" );
		log.info( "" );

		report.generateReport( reportParameters ).forEach( (k,v) -> {
			log.info( "  - " + k + ": " + v );
		});
		log.info( "" );
	}

	private void runGenerator() throws IOException {
		outputFileIsRequired();
		log.info( "Generating a random mock data..." );
		try ( val output = new FileWriter( outputFileName ) ) {
			val generator = WeatherMockDataGenerator.create();
			generator.generate( numOfObservations, output );
			log.info( "Mock data created at " + outputFileName );
		}
	}

	private String sortFile( String fileName ) throws IOException {
		log.info( "Sorting file..." );
		val sortedFileName = fileName + ".sorted";
		ExternalSort.mergeSortedFiles(
				Collections.singletonList( new ReadOnlyFile(fileName ) ),
				new File( sortedFileName ) );

		log.info( "New sorted file created at " + sortedFileName );
		return sortedFileName;
	}

	private void printFailureHandlerReport( FailureHandler failureHandler ) {
		if ( failureHandler.outputFile() != null ) {
			log.warn( "A few observations were ignored due to failures during the process." );
			log.warn( "All ignored observations were stored on an individual file at " + failureHandler.outputFile().toPath() );
		}
	}

	private void distanceIsRequired(){
		if ( distance == null )
			throw new IllegalArgumentException( "No distance unit defined" );
	}

	private void temperatureIsRequired(){
		if ( temperature == null )
			throw new IllegalArgumentException( "No distance unit defined" );
	}

	private void inputFileIsRequired(){
		if ( inputFileName == null || inputFileName.isEmpty() )
			throw new IllegalArgumentException( "Input file name not defined." );

		if ( !new File( inputFileName ).exists() )
			throw new IllegalArgumentException( "File not exists: " + inputFileName );
	}

	private void outputFileIsRequired(){
		if ( outputFileName == null || outputFileName.isEmpty() )
			throw new IllegalArgumentException( "Output file name not defined." );
	}

	enum Action { GENERATE, STATS, NORMALIZE }

	class ReadOnlyFile extends File {

		public ReadOnlyFile(String s) {
			super( s );
		}

		@Override
		public boolean delete() {
			return true;
		}
	}
}
