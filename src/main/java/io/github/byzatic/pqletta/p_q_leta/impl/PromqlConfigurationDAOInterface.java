package io.github.byzatic.pqletta.p_q_leta.impl;

import io.github.byzatic.commons.base_exceptions.OperationIncompleteException;
import io.github.byzatic.pqletta.p_q_leta.impl.local_dto.query_configuration.PrometheusQueryConfiguration;
import io.github.byzatic.pqletta.p_q_leta.impl.local_dto.server_request.PrometheusRequest;

public interface PromqlConfigurationDAOInterface {
    PrometheusQueryConfiguration get(PrometheusRequest prometheusRequest) throws OperationIncompleteException;
}
