package io.github.byzatic.pqletta.client.dto.response.impl.success;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Result {
    private Map<String, String> metric; // <-- универсальное решение
    private List<Value> values;

    public Result() {
    }

    private Result(Builder builder) {
        metric = builder.metric;
        values = builder.values;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Result copy) {
        Builder builder = new Builder();
        builder.metric = copy.getMetric();
        builder.values = copy.getValues();
        return builder;
    }

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

    /**
     * {@code Result} builder static inner class.
     */
    public static final class Builder {
        private Map<String, String> metric;
        private List<Value> values;

        private Builder() {
        }

        /**
         * Sets the {@code metric} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param metric the {@code metric} to set
         * @return a reference to this Builder
         */
        public Builder setMetric(Map<String, String> metric) {
            this.metric = metric;
            return this;
        }

        /**
         * Sets the {@code values} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param values the {@code values} to set
         * @return a reference to this Builder
         */
        public Builder setValues(List<Value> values) {
            this.values = values;
            return this;
        }

        /**
         * Returns a {@code Result} built from the parameters previously set.
         *
         * @return a {@code Result} built with parameters of this {@code Result.Builder}
         */
        public Result build() {
            return new Result(this);
        }
    }
}
