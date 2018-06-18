## The 'Weather Observations' Challenge
See: https://gist.github.com/dzeikei/83502df5d82188536435cdbd27789a8c

### Assumptions and Simplifications about the requirements
A few assumptions was made in order to get the challenge done.

- Temperature field would have decimal places.
- The location coordinates defined here is based on a basic cartesian
plane in which the distance between points of the same _distance unit_
would be easily calculated using the Euclidean Distance formula.
- The difference between two ordered coordinate points represents a
valid _traveled distance_.
- The timestamp isn't validated, although it is used to sort the dataset.
- The generated mock data wasn't shuffled or created with bad lines, although
the program is totally capable to deal with unordered or bad written lines.
- The only other country included on the generated data was BR, aside from AU, FR and US.
- As 'mean' has **[several meanings](https://en.wikipedia.org/wiki/Mean)** in mathematics,
I'm assuming mean as **[arithmetical mean](https://en.wikipedia.org/wiki/Arithmetic_mean)**.

### Technical Assumptions and Simplifications
1. Algorithms will always prioritize memory-efficiency from readability.
2. Algorithms will always prioritize the simplest approach to solve a problem, as
long as it respects the previous item.
3. Generification was applied only when two or more situations would take advantage
of that. Otherwise, I rather apply the item 2.
4. Assumed '\n' as default line ending for every text file read or written by this program.
5. Assumed 'ASCII' as default character encoding  for every text file read or written
by this program.
6. Used a third-party library to sort the files. It would take more time than I expect
to make an well-tested sort algorithm that would be able to do this on a memory-efficient
approach. Big Data frameworks (Hadoop and Spark to name a few) used to have this kind of
algorithm widely spread on the internet, I just chose the smallest one I've found.

### Code conventions
The following code conventions were adopted in order to keep the code as readable as possible.
1. One single package to wrap the code up. Once the domain is quite simple, I decided
to apply Robert C. Martin's 
**[Common Closure Principle](http://docs.google.com/a/cleancoder.com/viewer?a=v&pid=explorer&chrome=true&srcid=0BwhCYaYDn8EgOGM2ZGFhNmYtNmE4ZS00OGY5LWFkZTYtMjE0ZGNjODQ0MjEx&hl=en)**
and **[Common Reuse Principle](http://docs.google.com/a/cleancoder.com/viewer?a=v&pid=explorer&chrome=true&srcid=0BwhCYaYDn8EgOGM2ZGFhNmYtNmE4ZS00OGY5LWFkZTYtMjE0ZGNjODQ0MjEx&hl=en)**.
1. I avoided the use of getter and setter methods except when its usage make sense.
I've assumed that once all classes are wrapped up together on the very same package, and they were designed to
be used together (item 1).
1. Used private scopes only when didn't make send to expose internal methods or fields. On
most cases, methods and fields are _package private_.
1. Used public scopes only when required. On most cases, methods and fields are _package private_.
1. Widely used Lombok annotations for the sake of simplicity.
1. Adopted Kotlin's approach with Constructors: **[The primary constructor cannot contain any
code](https://kotlinlang.org/docs/reference/classes.html)**.
1. Secondary constructors, the one which required code, were defined as static utility method.
1. Immutable objects whenever possible, but opted out for mutable objects when
memory-efficiency were required. On this case, objects are not thread-safe.
1. Used Polyglot Maven in order to keep the project definition simple.
1. Also used a non-standard folder structure in order to keep it simple.

### Requirements
This program was written in Java 8 and requires Maven 3.5 (or earlier version)
in order to build properly. It was tested only on Linux, but would work fine on
other UNIX-like Operating System.

### Building the program
Open the _console_ on the directory you've extracted this source code and type:
```shell
$ mvn clean install
```
A jar package named **cammy-challenge-1.0.0.jar** would be generated at the **output**
directory.

### Running the program
This is the basic syntax:
```shell
$ java -jar cammy-challenge-1.0.0.jar <GENERATE|STATS|NORMALIZE> [options]
```
Where:
- _GENERATE_: generates a test file of representative data for use in simulation.
- _STATS_: generates a Statistical Report from a given input file.
- _NORMALIZE_: generate a normalized version of a given input file.

Available options:
- _-d_ \
      Distance unit. Required for actions STATS and NORMALIZE. \
      Possible Values: [KM, M, MILES]
- _-i_ \
      Input file name.
- _-o_ \
      Output file name.
- _-t_ \
      Temperature unit. Required for actions STATS and NORMALIZE. \
      Possible Values: [CELSIUS, FAHRENHEIT, KELVIN]

Options specific to the STATS action:
- _--maximum-temperature_ \
      Includes the maximum temperature on the Statistical Report \
      Default: false
- _--mean-temperature_ \
      Includes the mean temperature on the Statistical Report \
      Default: false
- _--minimum-temperature_ \
      Includes the minimum temperature on the Statistical Report \
      Default: false
- _--observations-by-observatory_ \
      Includes the number of observations from each observatory on the Statistical Report \
      Default: false
- _--total-distance-travelled_ \
      Includes the total distance travelled on the Statistical Report. \
      Default: false

Options specific to the GENERATE action:
- _-n_ \
      Number of observations to be generated. Required for action GENERATE. \
      Default: 1000000
      
### Sample Usage
Generating mock data
```shell
$ java -jar output/cammy-challenge-1.0.0.jar GENERATE \
  -o output/mock.data
```
Generating a report from the input file
```shell
$ java -jar output/cammy-challenge-1.0.0.jar STATS \
  -d KM -t CELSIUS -i output/mock.data \
  --maximum-temperature \
  --mean-temperature \
  --minimum-temperature \
  --observations-by-observatory \
  --total-distance-travelled
```
Normalizing the input file
```shell
$ java -jar output/cammy-challenge-1.0.0.jar NORMALIZE \
  -i output/mock.data \
  -o output/normalized.data \
  -d KM -t CELSIUS
```
