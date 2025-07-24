package io.github.byzatic.pqletta.client.impl;

import io.github.byzatic.commons.base_exceptions.OperationIncompleteException;
import io.github.byzatic.pqletta.client.dto.request.impl.PrometheusQueryRangeRequest;
import io.github.byzatic.pqletta.client.dto.response.PrometheusResponse;

public interface RangeQueryExecutorInterface {
    PrometheusResponse executePrometheusQuery(PrometheusQueryRangeRequest prometheusQueryRangeRequest) throws OperationIncompleteException;
}
