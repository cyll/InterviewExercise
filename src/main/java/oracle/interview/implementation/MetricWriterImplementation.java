package oracle.interview.implementation;

import oracle.interview.metrics.MetricStorage;
import oracle.interview.metrics.MetricWriter;
import oracle.interview.metrics.TargetMetricsContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.*;

/**
 * The class {@link MetricWriterImplementation} contains the logic need to store metrics and creates a timer that will
 * retry the Metrics that fail to me inserted
 * @author Christian Lopez
 * @version 1.0
 */
public class MetricWriterImplementation implements MetricWriter {
    //Property file that contains some info about the timer configuration
    private static final String PROPERTIES = "application.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(MetricWriterImplementation.class);
    private final MetricStorage storage;
    //This queue will contain the failed insertions
    Queue<TargetMetricsContainer> retryList = new LinkedList<>();

    /**
     * Methos that will create the object and setup the timer logic that will be use for the retry of the Metrics
     * pulling information from a properties file
     * @param storage {@link MetricStorage} object that contain the logic for the Metric store
     * @author Christian Lopez
     */
    public MetricWriterImplementation(MetricStorage storage) {
        this.storage = storage;
        URL libraryURL = ClassLoader.getSystemClassLoader().getResource(PROPERTIES);
        try (InputStream input = new FileInputStream(new File(URLDecoder.decode(libraryURL.getFile(), Charset.defaultCharset())))) {

            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    LOGGER.info("Timer running ");
                    if(!retryList.isEmpty()) {
                        TargetMetricsContainer targetMetricsContainer = retryList.poll();
                        LOGGER.info("Executing again: " + targetMetricsContainer);
                        writeMetricsContainer(targetMetricsContainer);
                    }
                }
            };
            timer.schedule(task, Integer.parseInt(prop.getProperty("initTimer")),  Integer.parseInt(prop.getProperty("timerRun")));
        } catch (IOException ex) {
            LOGGER.error("Error getting properties File");
        }


    }

    /**
     * This method perfom call the {@link MetricStorage} object and execute the write methos to store the metric information
     * contained in {@link TargetMetricsContainer} object, If the write throws {@link SQLException} it will add the {@link TargetMetricsContainer}
     * object to a queue for a retry later via Timer
     * @param metricsContainer Object that has all the metric information available
     * @author Christian Lopez
     */
    @Override
    public void writeMetricsContainer(TargetMetricsContainer metricsContainer) {
        try {
            if(metricsContainer == null)
                throw new NullPointerException("The metric container cannot be null");
            storage.write(metricsContainer);
            LOGGER.info("Metric inserted " + metricsContainer);
        }catch (SQLException exception){
            LOGGER.error("Error while inserting " + metricsContainer);
            retryList.add(metricsContainer);
        }

    }
}
