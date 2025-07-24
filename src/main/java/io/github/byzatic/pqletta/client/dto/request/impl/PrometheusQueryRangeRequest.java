package io.github.byzatic.pqletta.client.dto.request.impl;

import org.jetbrains.annotations.NotNull;
import io.github.byzatic.pqletta.client.dto.request.QueryRange;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class PrometheusQueryRangeRequest implements QueryRange {
    private String prometheusQuery;
    private Instant start;
    private Instant end;
    private Duration step;
    private URL url;
    private Boolean sslVerify;

    private PrometheusQueryRangeRequest() {
    }

    private PrometheusQueryRangeRequest(Builder builder) {
        prometheusQuery = builder.prometheusQuery;
        start = builder.start;
        end = builder.end;
        step = builder.step;
        url = builder.url;
        sslVerify = builder.sslVerify;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(@NotNull PrometheusQueryRangeRequest copy) {
        Builder builder = new Builder();
        builder.prometheusQuery = copy.getPrometheusQuery();
        builder.start = copy.getStart();
        builder.end = copy.getEnd();
        builder.step = copy.getStep();
        builder.url = copy.getUrl();
        builder.sslVerify = copy.getSslVerify();
        return builder;
    }

    public String getPrometheusQuery() {
        return prometheusQuery;
    }

    public Instant getStart() {
        return start;
    }

    public Instant getEnd() {
        return end;
    }

    /**
     * The step defines the resolution of the time series, i.e., the intervals at which Prometheus should return values when executing a range query.
     * In Prometheus, the step parameter is always measured in seconds using Duration.ofSeconds().
     * @return the time interval (step) between data points in the Prometheus range query
     */
    public Duration getStep() {
        return step;
    }

    public URL getUrl() {
        return url;
    }

    public Boolean getSslVerify() {
        return sslVerify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrometheusQueryRangeRequest that = (PrometheusQueryRangeRequest) o;
        return Objects.equals(prometheusQuery, that.prometheusQuery) && Objects.equals(start, that.start) && Objects.equals(end, that.end) && Objects.equals(step, that.step) && Objects.equals(url, that.url) && Objects.equals(sslVerify, that.sslVerify);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prometheusQuery, start, end, step, url, sslVerify);
    }

    @Override
    public String toString() {
        return "PrometheusQueryRangeRequest{" +
                "prometheusQuery='" + prometheusQuery + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", step=" + step +
                ", url=" + url +
                ", sslVerify=" + sslVerify +
                '}';
    }

    /**
     * {@code PrometheusQueryRangeRequest} builder static inner class.
     */
    public static final class Builder {
        private String prometheusQuery;
        private Instant start;
        private Instant end;
        private Duration step;
        private URL url;
        private Boolean sslVerify;

        private Builder() {
        }

        /**
         * Sets the {@code prometheusQuery} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param prometheusQuery the {@code prometheusQuery} to set
         * @return a reference to this Builder
         */
        public Builder setPrometheusQuery(@NotNull String prometheusQuery) {
            this.prometheusQuery = prometheusQuery;
            return this;
        }

        /**
         * Sets the {@code start} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param start the {@code start} to set
         * @return a reference to this Builder
         */
        public Builder setStart(@NotNull Instant start) {
            this.start = start;
            return this;
        }

        /**
         * Sets the {@code end} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param end the {@code end} to set
         * @return a reference to this Builder
         */
        public Builder setEnd(@NotNull Instant end) {
            this.end = end;
            return this;
        }

        /**
         * Sets the {@code step} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param step the {@code step} to set
         * @return a reference to this Builder
         */
        public Builder setStep(@NotNull Duration step) {
            this.step = step;
            return this;
        }

        /**
         * Sets the {@code url} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param url the {@code url} to set
         * @return a reference to this Builder
         */
        public Builder setUrl(@NotNull URL url) {
            this.url = url;
            return this;
        }

        /**
         * Sets the {@code sslVerify} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param sslVerify the {@code sslVerify} to set
         * @return a reference to this Builder
         */
        public Builder setSslVerify(@NotNull Boolean sslVerify) {
            this.sslVerify = sslVerify;
            return this;
        }

        /**
         * Returns a {@code PrometheusQueryRangeRequest} built from the parameters previously set.
         *
         * @return a {@code PrometheusQueryRangeRequest} built with parameters of this {@code PrometheusQueryRangeRequest.Builder}
         */
        public PrometheusQueryRangeRequest build() {
            return new PrometheusQueryRangeRequest(this);
        }
    }
}
