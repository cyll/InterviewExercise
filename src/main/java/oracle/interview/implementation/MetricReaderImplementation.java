package oracle.interview.implementation;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import oracle.interview.model.Metric;
import oracle.interview.model.MetricCollection;
import oracle.interview.metrics.MetricReader;
import oracle.interview.metrics.TargetMetricsContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.*;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * The class {@link MetricWriterImplementation} contains the logic need to map Metric XML objects and convert them into
 * {@link TargetMetricsContainer} list
 * @author Christian Lopez
 * @version 1.0
 */
public class MetricReaderImplementation implements MetricReader {
    private final static Logger LOGGER = LoggerFactory.getLogger(MetricReaderImplementation.class);
    private final static String DATE_PATTERN="d-MMM-yyyy";

    /**
     * This method will map the XML to Java Object and return a list of {@link TargetMetricsContainer}
     * @author Christian Lopez
     * @param metricInputStream Input stream that contains XML
     * @return A list of {@link TargetMetricsContainer}
     */
    @Override
    public List<TargetMetricsContainer> readMetrics(InputStream metricInputStream)  {
        //Create and configure the Jackson to map the XML
        JacksonXmlModule module = new JacksonXmlModule();
        //Setup the use of the annotation inside of the models
        module.setDefaultUseWrapper(false);
        //Mapper object that will map the XML to a MetricCollection object
        XmlMapper mapper = new XmlMapper(module);
       try {
           MetricCollection metricCollection = mapper.readValue(metricInputStream, MetricCollection.class);
           LOGGER.debug("Parsed object " + metricCollection );
           //Helpe class that will help to transform MetricCollection to a list of  TargetMetricsContainer
           return createTargetMetricsContainerList(metricCollection);
       }catch (IOException ex) {
           LOGGER.error("Error while parsing XML ", ex);
       }
       return null;
    }

    /**
     * This method transform the {@link MetricCollection} object into a {@link TargetMetricsContainer} and return a list of {@link TargetMetricsContainer}
     * @param metricCollection This object will contain all the information about the metrics and the target
     * @return A list of {@link TargetMetricsContainer}
     */
    private List<TargetMetricsContainer> createTargetMetricsContainerList(MetricCollection metricCollection){
        //Create a formatter that will parse the value received from the XML
        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern(DATE_PATTERN).withLocale(Locale.ENGLISH);;
        return  metricCollection.getTarget().stream().map(
                element -> {
                    TargetMetricsContainer targetMetricsContainer = new TargetMetricsContainer(element.getName(),element.getType());
                    for(Metric metric : element.getMetric()){
                        //Here we create the date from the metric
                        LocalDate localDate = LocalDate.from(dateTimeFormatter.parse(metric.getTimestamp()));
                        //Adding the metric 1-1, The Date received on the XML does not contain time so we put this to use the start of the day time to create the Instant
                        targetMetricsContainer.addMetric(metric.getType(), localDate.atStartOfDay(ZoneId.systemDefault()).toInstant(),Integer.parseInt(metric.getValue()));
                    }
                    return targetMetricsContainer;
                })
        .collect(Collectors.toList());
    }
}
