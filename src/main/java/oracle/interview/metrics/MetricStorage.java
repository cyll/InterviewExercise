package oracle.interview.metrics;

import java.sql.SQLException;

public interface MetricStorage extends AutoCloseable {
    void write(TargetMetricsContainer container) throws SQLException;
    void close();
}
