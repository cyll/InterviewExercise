package oracle.interview.metrics;

import java.sql.SQLException;
import java.util.Random;

public class RandomlyFailingMetricStorage implements MetricStorage {

    public static class RandomFailure {

        public boolean isFailureCondition() {
            return new Random().nextInt(5) % 3 == 0;
        }
    }
    RandomFailure randomFailure = new RandomFailure();

    @Override
    public void write(TargetMetricsContainer container) throws SQLException {
        if (randomFailure.isFailureCondition()) {
            throw new SQLException("error writing to database");
        }

        System.out.println("wrote data for " + container.getTargetName());
    }

    @Override
    public void close() {
        System.out.println("closing metric storage");
        // closes storage
    }
}
