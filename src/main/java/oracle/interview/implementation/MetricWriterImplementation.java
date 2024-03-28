package oracle.interview.implementation;

import oracle.interview.metrics.MetricStorage;
import oracle.interview.metrics.MetricWriter;
import oracle.interview.metrics.TargetMetricsContainer;

public class MetricWriterImplementation implements MetricWriter {

    private final MetricStorage storage;

    public MetricWriterImplementation(MetricStorage storage) {
        this.storage = storage;
    }

    @Override
    public void writeMetricsContainer(TargetMetricsContainer metricsContainer) {
        // TODO: write this metricsContainer to the MetricStorage. Hint
        //      storage.write(metricsContainer);
        //  partially works.  Since the write could fail, retry the write on failure
        //  as appropriate.

    }
}
