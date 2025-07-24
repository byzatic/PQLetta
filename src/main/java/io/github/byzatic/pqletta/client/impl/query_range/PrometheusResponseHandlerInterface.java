package io.github.byzatic.pqletta.client.impl.query_range;

import org.jetbrains.annotations.NotNull;
import io.github.byzatic.pqletta.client.dto.response.PrometheusResponse;
import io.github.byzatic.pqletta.client.exceptions.InternalClientException;

public interface PrometheusResponseHandlerInterface {
    @NotNull PrometheusResponse processResponse(@NotNull String responseBodyJson, @NotNull Integer httpStatusCode) throws InternalClientException;
}
