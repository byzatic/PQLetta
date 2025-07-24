package io.github.byzatic.pqletta.p_q_leta.impl;

import io.github.byzatic.commons.base_exceptions.OperationIncompleteException;
import io.github.byzatic.pqletta.client.dto.request.impl.PrometheusQueryRangeRequest;
import io.github.byzatic.pqletta.p_q_leta.impl.local_dto.query_configuration.PrometheusQueryConfiguration;

public interface PromqlTemplateRepositoryInterface {
    PrometheusQueryRangeRequest getPrometheusServerRequest(PrometheusQueryConfiguration prometheusQueryConfiguration) throws OperationIncompleteException;
}
