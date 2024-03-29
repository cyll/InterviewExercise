package implementation;

import oracle.interview.implementation.MetricReaderImplementation;
import oracle.interview.metrics.MetricReader;
import oracle.interview.metrics.TargetMetricsContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class MetricReaderImplementationTest {

    private static final String RESOURCE_NAME = "test_metrics_data.xml";
    private File testFile;
    private MetricReader reader = new MetricReaderImplementation();
    @BeforeEach
    public void init(){
        URL libraryURL = ClassLoader.getSystemClassLoader().getResource(RESOURCE_NAME);
        if (libraryURL == null) {
            throw new IllegalStateException("file is missing from resources: " + RESOURCE_NAME);
        }
         testFile = new File(URLDecoder.decode(libraryURL.getFile(), Charset.defaultCharset()));
    }
    MetricReaderImplementation metricReaderImplementation;
    @Test
    public void readMetricsCheckListNotNullTest() throws IOException, ParserConfigurationException, SAXException {
        try(FileInputStream fis = new FileInputStream(testFile)){
            List<TargetMetricsContainer> targetMetricsContainerList = reader.readMetrics(fis);
            assertNotNull(targetMetricsContainerList);
        }
    }

    @Test
    public void readMetricsCheckCorrectNumberTest() throws IOException, ParserConfigurationException, SAXException {
        try(FileInputStream fis = new FileInputStream(testFile)){
            List<TargetMetricsContainer> targetMetricsContainerList = reader.readMetrics(fis);
            assertEquals(3,targetMetricsContainerList.size());
        }
    }

    @Test
    public void readMetricsCheckCorrectNumberMetricsTest() throws IOException, ParserConfigurationException, SAXException {
        try(FileInputStream fis = new FileInputStream(testFile)){
            List<TargetMetricsContainer> targetMetricsContainerList = reader.readMetrics(fis);
            assertEquals(14,targetMetricsContainerList.get(0).getPayload().size());
        }
    }
    @Test
    public void readMetricsCheckSpecificMetricValueTest() throws IOException, ParserConfigurationException, SAXException {
        try(FileInputStream fis = new FileInputStream(testFile)){
            List<TargetMetricsContainer> targetMetricsContainerList = reader.readMetrics(fis);
            targetMetricsContainerList.get(0).getPayload().get(2).get("type");
            assertEquals("cpu",targetMetricsContainerList.get(0).getPayload().get(2).get("type"));
            assertEquals(20,targetMetricsContainerList.get(0).getPayload().get(2).get("value"));
        }
    }

    @Test
    public void readMetricsCheckParamIsNullTest() throws IOException, ParserConfigurationException, SAXException {
        try(FileInputStream fis = new FileInputStream(testFile)){
            assertThrows(IllegalArgumentException.class, () -> {
                List<TargetMetricsContainer> targetMetricsContainerList = reader.readMetrics(null);
            });
        }
    }
}
