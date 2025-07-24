package io.github.byzatic.pqletta.client.impl.query_range.response_handler.dto;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Result {
    private Map<String, String> metric; // <-- универсальное решение
    private List<Value> values;

    public Map<String, String> getMetric() {
        return metric;
    }

    public List<Value> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(metric, result.metric) && Objects.equals(values, result.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metric, values);
    }

    @Override
    public String toString() {
        return "Result{" +
                "metric=" + metric +
                ", values=" + values +
                '}';
    }
}
