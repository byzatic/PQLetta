package io.github.byzatic.pqletta.p_q_leta.impl.template_repository.template_processor;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.byzatic.commons.base_exceptions.OperationIncompleteException;
import io.github.byzatic.pqletta.p_q_leta.impl.local_dto.query_configuration.PrometheusQueryConfiguration;
import io.github.byzatic.pqletta.p_q_leta.impl.local_dto.query_configuration.QueryLabel;
import io.github.byzatic.pqletta.p_q_leta.impl.local_dto.query_configuration.QueryParameter;
import io.github.byzatic.pqletta.p_q_leta.impl.template_repository.PrometheusQueryTemplateProcessorInterface;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrometheusQueryTemplateProcessor implements PrometheusQueryTemplateProcessorInterface {
    private final static Logger logger = LoggerFactory.getLogger(PrometheusQueryTemplateProcessor.class);
    Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);

    @NotNull
    @Override
    public String processTemplate(@NotNull String templateStr, @NotNull PrometheusQueryConfiguration prometheusQueryConfiguration) throws OperationIncompleteException {
        try {
            logger.debug("Process template {} with configuration {}", templateStr, prometheusQueryConfiguration);
            Template template = new Template("promql", templateStr, cfg);

            Map<String, Object> model = new HashMap<>();

            model.put("TAGLINEPLACEHOLDER", toPromqlLabelSelector(prometheusQueryConfiguration.getQueryLabels()));

            for (QueryParameter queryParameter : prometheusQueryConfiguration.getQueryParameters()) {
                model.put(queryParameter.getPlaceholder(), queryParameter.getReplacement());
            }

            StringWriter writer = new StringWriter();
            template.process(model, writer);
            String finalQuery = writer.toString();

            return finalQuery;

        } catch (Exception e) {
            throw new OperationIncompleteException(e);
        }
    }


    private String toPromqlLabelSelector(List<QueryLabel> labels) {
        logger.debug("PromqlLabelSelector labels {}", labels);

        if (labels.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("{");

        for (int i = 0; i < labels.size(); i++) {
            QueryLabel label = labels.get(i);
            builder.append(label.getKey())
                    .append(label.getSign())
                    .append("'")
                    .append(label.getValue())
                    .append("'");

            if (i < labels.size() - 1) {
                builder.append(",");
            }
        }

        builder.append("}");

        String result = builder.toString();

        logger.debug("PromqlLabelSelector result {}", result);

        return result;
    }

}
