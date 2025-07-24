package io.github.byzatic.pqletta.p_q_leta.impl.template_repository.formatter;

import org.jetbrains.annotations.NotNull;
import io.github.byzatic.commons.ObjectsUtils;
import io.github.byzatic.pqletta.p_q_leta.impl.template_repository.PrometheusQueryTemplateFormatterInterface;

public class PrometheusQueryTemplateFormatter implements PrometheusQueryTemplateFormatterInterface {

    @Override
    @NotNull
    public String format(@NotNull String promqlRaw) {
        ObjectsUtils.requireNonNull(promqlRaw, new IllegalArgumentException("PromQL raw query string should be NotNull"));
        String processingPromqlRaw = String.copyValueOf(promqlRaw.toCharArray());
        return normalizePromqlWhitespace(processingPromqlRaw);
    }

    @NotNull
    public String normalizePromqlWhitespace(@NotNull String promqlRaw) {

        String processingPromqlRaw = String.copyValueOf(promqlRaw.toCharArray());

        return processingPromqlRaw
                // нормализуем логические операторы
                .replaceAll("(?i)\\)OR\\(", ") OR (")
                .replaceAll("(?i)\\)AND\\(", ") AND (")

                // пробелы вокруг логики и арифметики
                .replaceAll("(?i)\\band\\b", " and ")
                .replaceAll("(?i)\\bor\\b", " or ")
                .replaceAll("(?i)\\bbool\\b", " bool ")
                .replaceAll("(?i)\\bon\\b", " on ")
                .replaceAll("(?i)\\bvector\\b", " vector ")

                .replaceAll("\\+", " + ")
                .replaceAll("-", " - ")
                .replaceAll("\\*", " * ")
                .replaceAll("/", " / ")
                .replaceAll("==", " == ")
                .replaceAll("!=", " != ")
                .replaceAll(">=", " >= ")
                .replaceAll("<=", " <= ")
                .replaceAll("(?<=[^<>=])>(?=[^=])", " > ")
                .replaceAll("(?<=[^<>=])<(?=[^=])", " < ")

                // пробелы вокруг подстановок переменных
                .replaceAll("(?<!\\s)(\\$\\{[^}]+\\})(?!\\s)", " $1 ")

                // убрать лишние пробелы
                .replaceAll("\\s+", " ")
                .replaceAll("\\(\\s+", "(")
                .replaceAll("\\s+\\)", ")")
                .replaceAll("\\{\\s+", "{")
                .replaceAll("\\s+\\}", "}")

                // убрать пробел между метрикой и лейблами
                .replaceAll("([a-zA-Z0-9_]+)\\s+\\{", "$1{")

                // убрать пробел между vector и скобками
                .replaceAll("vector\\s+\\(", "vector(")

                // убрать пробел между on и ()
                .replaceAll("on\\s+\\(\\)", "on()")

                .trim();
    }
}
