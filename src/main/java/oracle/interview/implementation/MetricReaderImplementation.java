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

public class MetricReaderImplementation implements MetricReader {
    private final static Logger LOGGER = LoggerFactory.getLogger(MetricReaderImplementation.class);
    private final static String DATE_PATTERN="d-MMM-yyyy";
    @Override
    public List<TargetMetricsContainer> readMetrics(InputStream metricInputStream)  {

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper mapper = new XmlMapper(module);
       try {
           MetricCollection metricCollection = mapper.readValue(metricInputStream, MetricCollection.class);
           LOGGER.debug("Parsed object " + metricCollection );
           return createTargetMetricsContainerList(metricCollection);
       }catch (IOException ex) {
           LOGGER.error("Error while parsing XML ", ex);
       }
       return null;
    }

    private List<TargetMetricsContainer> createTargetMetricsContainerList(MetricCollection metricCollection){
        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern(DATE_PATTERN).withLocale(Locale.ENGLISH);;
        return  metricCollection.getTarget().stream().map(
                element -> {
                    TargetMetricsContainer targetMetricsContainer = new TargetMetricsContainer(element.getName(),element.getType());
                    for(Metric metric : element.getMetric()){
                        LocalDate localDate = LocalDate.from(dateTimeFormatter.parse(metric.getTimestamp()));
                        targetMetricsContainer.addMetric(metric.getType(), localDate.atStartOfDay(ZoneId.systemDefault()).toInstant(),Integer.parseInt(metric.getValue()));
                    }
                    return targetMetricsContainer;
                })
        .collect(Collectors.toList());
    }
}
