package oracle.interview.implementation;

import oracle.interview.metrics.MetricReader;
import oracle.interview.metrics.TargetMetricsContainer;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class MetricReaderImplementation implements MetricReader {
    @Override
    public List<TargetMetricsContainer> readMetrics(InputStream metricInputStream) {
        // TODO: implement this, reading data from the input stream, returning a list of containers read from the stream
        return Collections.emptyList();
    }

}
