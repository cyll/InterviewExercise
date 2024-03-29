package oracle.interview.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class Target implements Serializable {
    @JacksonXmlProperty(isAttribute = true)
    private String name;
    @JacksonXmlProperty(isAttribute = true)
    private String type;
    @JacksonXmlProperty
    private String description;
    @JacksonXmlProperty
    private List<Metric> metric;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Metric> getMetric() {
        return metric;
    }

    public void setMetric(List<Metric> metric) {
        this.metric = metric;
    }

    @Override
    public String toString() {
        return "Target{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", metric=" + metric +
                '}';
    }
}
