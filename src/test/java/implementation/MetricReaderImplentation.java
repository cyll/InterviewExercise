package implementation;

import oracle.interview.implementation.MetricWriterImplementation;
import oracle.interview.metrics.MetricStorage;
import oracle.interview.metrics.TargetMetricsContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MetricReaderImplentation {
    TargetMetricsContainer targetMetricsContainer;
    MetricWriterImplementation metricWriterImplementation;
    @Mock
    MetricStorage metricStorage;
   @BeforeEach
    public void init(){
       MockitoAnnotations.initMocks(this);
       metricWriterImplementation = new MetricWriterImplementation(metricStorage);
       targetMetricsContainer = new TargetMetricsContainer("oracle.com", "host" );
       targetMetricsContainer.addMetric("cpu", Instant.parse("2024-11-30T18:35:24.00Z"), 80);
       targetMetricsContainer.addMetric("memory", Instant.parse("2024-11-30T18:35:24.00Z"), 1024);
       targetMetricsContainer.addMetric("cpu", Instant.parse("2024-12-30T18:35:24.00Z"), 60);
       targetMetricsContainer.addMetric("memory", Instant.parse("2024-12-30T18:35:24.00Z"), 124);
   }

   @Test
    public void writeMetricsContainerNullParam() {
       assertThrows(NullPointerException.class, () -> {
          metricWriterImplementation.writeMetricsContainer(null);
       });
   }
   @Test
    public void writeMetricsContainerCorrectInsertion() throws SQLException {
       doNothing().when(metricStorage).write(any());
       metricWriterImplementation.writeMetricsContainer(targetMetricsContainer);
       verify(metricStorage, times(1)).write(any());
   }
    @Test
    public void writeMetricsContainerIncorrectInsertion() throws SQLException {
        doThrow(SQLException.class).when(metricStorage).write(any());
        metricWriterImplementation.writeMetricsContainer(targetMetricsContainer);

        verify(metricStorage, times(1)).write(any());
    }

}
