package io.github.byzatic.pqletta.p_q_leta;

import io.github.byzatic.commons.base_exceptions.OperationIncompleteException;
import io.github.byzatic.pqletta.client.dto.response.PrometheusResponse;

// От “Prometheus Query Palette” — палитра параметризуемых запросов Prometheus
public interface PQletaInterface {
    PrometheusResponse processQuery(QueryDescription queryDescription) throws OperationIncompleteException;
}
