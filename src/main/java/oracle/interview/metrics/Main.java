package oracle.interview.metrics;

import oracle.interview.implementation.MetricReaderImplementation;
import oracle.interview.implementation.MetricWriterImplementation;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        MetricReader reader = new MetricReaderImplementation();
        try (FileInputStream fis = new FileInputStream(findFile("metrics_data.xml"))) {
            List<TargetMetricsContainer> metrics = reader.readMetrics(fis);
            writeAllMetrics(metrics);
        }
    }

    private static void writeAllMetrics(List<TargetMetricsContainer> metrics) {

        try (RandomlyFailingMetricStorage storage = new RandomlyFailingMetricStorage()) {
            MetricWriter writer = new MetricWriterImplementation(storage);
            for (TargetMetricsContainer metric : metrics) {
                writer.writeMetricsContainer(metric);
            }
        }
    }

    public static File findFile(String name) {
        URL libraryURL = ClassLoader.getSystemClassLoader().getResource(name);
        if (libraryURL == null) {
            throw new IllegalStateException("file is missing from resources: " + name);
        }
        return new File(URLDecoder.decode(libraryURL.getFile(), Charset.defaultCharset()));
    }

}
