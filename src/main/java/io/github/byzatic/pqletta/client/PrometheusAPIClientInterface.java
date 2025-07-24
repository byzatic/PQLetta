package io.github.byzatic.pqletta.client;

import io.github.byzatic.commons.base_exceptions.OperationIncompleteException;
import io.github.byzatic.pqletta.client.dto.response.PrometheusResponse;
import io.github.byzatic.pqletta.client.dto.response.impl.success.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface PrometheusAPIClientInterface {

    PrometheusResponse query(String promql) throws OperationIncompleteException;

    PrometheusResponse queryRange(String promql, Instant start, Instant end, Duration step) throws OperationIncompleteException;

    List<PrometheusResponse> getSeries(String matcher, Instant start, Instant end) throws OperationIncompleteException;

    // TODO: It is not the best design to use Map<String, List<String>>
    Map<String, List<String>> getLabelValues(String labelName) throws OperationIncompleteException;

    List<PrometheusResponse> getTargets() throws OperationIncompleteException;

    List<PrometheusResponse> getAlerts() throws OperationIncompleteException;

    List<PrometheusResponse> getRules() throws OperationIncompleteException;

    List<PrometheusResponse> getMetadata(String metricName) throws OperationIncompleteException;
}
