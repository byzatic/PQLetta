package io.github.byzatic.pqletta.p_q_leta.impl.template_repository;

import io.github.byzatic.commons.base_exceptions.OperationIncompleteException;

import java.nio.file.Path;
import java.util.Map;

public interface PrometheusQueryTemplatesParserInterface {
    Map<String, String> parse(Path configFilePath) throws OperationIncompleteException;
}
