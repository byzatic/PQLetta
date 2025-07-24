package io.github.byzatic.pqletta.p_q_leta.impl.template_repository.parser;

import org.jetbrains.annotations.NotNull;
import io.github.byzatic.commons.ObjectsUtils;
import io.github.byzatic.commons.base_exceptions.OperationIncompleteException;
import io.github.byzatic.pqletta.p_q_leta.impl.template_repository.PrometheusQueryTemplatesParserInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PrometheusQueryTemplatesParser implements PrometheusQueryTemplatesParserInterface {

    @Override
    @NotNull
    public Map<String, String> parse(@NotNull Path configFilePath) throws OperationIncompleteException {
        try {
            ObjectsUtils.requireNonNull(configFilePath, new IllegalArgumentException("PromQL templates file should be NotNull"));
            return parsePromqlFile(configFilePath);
        } catch (Exception e) {
            throw new OperationIncompleteException(e);
        }
    }

    private Map<String, String> parsePromqlFile(Path path) throws IOException {
        Map<String, String> result = new LinkedHashMap<>();

        List<String> lines = Files.readAllLines(path);
        String currentType = null;
        StringBuilder promqlBuilder = new StringBuilder();
        boolean skipBlock = false;

        for (String line : lines) {
            line = line.trim();

            // Начало блока комментария
            if (line.equals("//*comment*")) {
                skipBlock = true;
                continue;
            }

            // Конец блока комментария
            if (line.equals("*comment*//")) {
                skipBlock = false;
                continue;
            }

            if (skipBlock || line.isEmpty()) {
                continue;
            }

            if (line.startsWith("# type:")) {
                if (currentType != null && !promqlBuilder.isEmpty()) {
                    result.put(currentType, promqlBuilder.toString());
                }
                currentType = line.substring(7).trim();
                promqlBuilder.setLength(0); // reset
            } else {
                promqlBuilder.append(line).append(" ");
            }
        }

        // Последний блок
        if (currentType != null && !promqlBuilder.isEmpty()) {
            result.put(currentType, promqlBuilder.toString());
        }

        return result;
    }
}
