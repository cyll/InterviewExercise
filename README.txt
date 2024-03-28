
Oracle's Enterprise Manager collects data about computing systems.  That data is stored, aggregated,
and made available to users so they can better administer their systems.

This exercise is a simulation of metric gathering and storage that is a simplification of how
Enterprise Manager collects metrics.  You should supply the code to read data from an XML file
and to write the data to a MetricStorage class.  Run Main.java to execute the program.  You'll
need to implement the missing functionality in the following two classes:
    MetricReaderImplementation: Read data from an XML file.  Look for the
                "TODO" to read data from the input stream
                If you are not familiar with XML parsing there are sites and youtube
                videos that explain it in detail.
    MetricWriterImplementation: Write data to a MetricStorage class.  Look for
                the "TODO" to write data to the storage.  Some writes may fail, so
                handle SQLExceptions appropriately to avoid losing data.

Please return any classes you've created, and unit tests if you have them.  Be sure to use functional
decomposition, name variables well, and include comments where necessary.

---------------------------------------------------------------------------------------------------------


Additional details about the simulation

Terminology
    metric: a name, value, and timestamp that represents the state of a system (for example: CPU load)
    target: an entity upon which metrics are gathered (for example: a host machine).  A target is
        uniquely identified by its (name, type) tuple
            (for example: ("mydatabase", "oracle_database") or ("red.oracle.com", "host") are unique
            identifiers)


There are three provided interfaces
    MetricReader: reads metrics from an InputStream and provides a way to access metrics
    MetricWriter: writes metrics to the storage
    MetricStorage: stores TargetMetricsContainers to permanent storage

There are three concrete classes which you are not expected to change
    TargetMetricsContainer: this contains a list of metrics that have been collected for a target
    RandomlyFailingMetricStorage: this is a mock of a storage facility for metrics, this will write the data
        to stdout to simulate the writing.  Be aware that the simulated writes can fail randomly.
    Main: this coordinates the reader and writer


Gradle

There is a build.gradle supplied, if you wish to use it.  To build the project, use "gradle clean build".
Please feel free to use whichever IDE you are most familiar with.  If you do choose to use the gradle
project and are behind a VPN, you may need to use the offline gradle flag ("gradle --offline build")
or uncomment the offline start parameter in init.gradle.
