package oracle.interview.metrics;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TargetMetricsContainer {
    private final String targetName;
    private final String targetType;
    private final List<Map<String,Object>> payload = new ArrayList<>();

    public TargetMetricsContainer(String targetName, String targetType) {
        this.targetName = targetName;
        this.targetType = targetType;
    }

    public void addMetric(String metricName, Instant timestamp, int metricValue) {
        Map<String, Object> metricPayload = new java.util.HashMap<>();
        metricPayload.put("type", metricName);
        metricPayload.put("timestamp", timestamp);
        metricPayload.put("value", metricValue);
        payload.add(metricPayload);
    }

    public List<Map<String,Object>> getPayload() {
        return payload;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getTargetType() {
        return targetType;
    }

    public int getTargetHash() {
        return Objects.hash(targetName, targetType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TargetMetricsContainer container = (TargetMetricsContainer) o;
        return Objects.equals(targetName, container.targetName) && Objects.equals(targetType, container.targetType) && Objects.equals(payload, container.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetName, targetType, payload);
    }

    @Override
    public String toString() {
        return "MetricContainer{" +
                "targetName='" + targetName + '\'' +
                ", targetType='" + targetType + '\'' +
                ", payload=" + payload +
                '}';
    }
}
