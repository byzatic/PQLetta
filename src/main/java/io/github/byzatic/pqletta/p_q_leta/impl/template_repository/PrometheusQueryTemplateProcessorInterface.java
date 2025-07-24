package io.github.byzatic.pqletta.p_q_leta.impl.template_repository;

import org.jetbrains.annotations.NotNull;
import io.github.byzatic.commons.base_exceptions.OperationIncompleteException;
import io.github.byzatic.pqletta.p_q_leta.impl.local_dto.query_configuration.PrometheusQueryConfiguration;

public interface PrometheusQueryTemplateProcessorInterface {
    @NotNull String processTemplate(@NotNull String templateStr, @NotNull PrometheusQueryConfiguration prometheusQueryConfiguration) throws OperationIncompleteException;
}
