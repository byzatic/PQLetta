package io.github.byzatic.pqletta.p_q_leta.impl.template_repository;

import org.jetbrains.annotations.NotNull;

public interface PrometheusQueryTemplateFormatterInterface {
    @NotNull String format(@NotNull String promqlRaw);
}
