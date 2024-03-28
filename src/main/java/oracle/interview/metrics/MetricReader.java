package oracle.interview.metrics;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface MetricReader {
    String TARGET_NODE_TYPE = "target";
    String METRIC_NODE_TYPE = "metric";
    String METRIC_NAME_ATTRIBUTE = "name";
    String METRIC_TYPE_ATTRIBUTE = "type";

    List<TargetMetricsContainer> readMetrics(InputStream metricInputStream) throws ParserConfigurationException, IOException, SAXException;
}
