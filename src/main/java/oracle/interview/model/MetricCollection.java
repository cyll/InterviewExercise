package oracle.interview.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.List;

public class MetricCollection implements Serializable {
    @JacksonXmlProperty
    private List<Target> target;

    public List<Target> getTarget() {
        return target;
    }

    public void setTarget(List<Target> target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "MetricCollection{" +
                "target=" + target +
                '}';
    }
}
