package io.github.byzatic.pqletta.p_q_leta.impl.template_repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.byzatic.commons.ObjectsUtils;
import io.github.byzatic.commons.base_exceptions.OperationIncompleteException;
import io.github.byzatic.pqletta.client.dto.request.impl.PrometheusQueryRangeRequest;
import io.github.byzatic.pqletta.p_q_leta.impl.PromqlTemplateRepositoryInterface;
import io.github.byzatic.pqletta.p_q_leta.impl.local_dto.query_configuration.PrometheusQueryConfiguration;

import java.net.URL;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class PromqlTemplateRepository implements PromqlTemplateRepositoryInterface {
    private final static Logger logger = LoggerFactory.getLogger(PromqlTemplateRepository.class);

    private final Map<String, String> prometheusQueryTemplates = new HashMap<>();

    private final PrometheusQueryTemplateProcessorInterface prometheusQueryTemplateProcessor;


    public PromqlTemplateRepository(PrometheusQueryTemplatesParserInterface prometheusQueryTemplatesParser, PrometheusQueryTemplateFormatterInterface prometheusQueryTemplateFormatter, PrometheusQueryTemplateProcessorInterface prometheusQueryTemplateProcessor, Path configFilePath) throws OperationIncompleteException {
        try {
            Map<String, String> prometheusRawQueryTemplates = prometheusQueryTemplatesParser.parse(configFilePath);
            for (Map.Entry<String, String> promQueryTypeBodySet : prometheusRawQueryTemplates.entrySet()) {
                prometheusQueryTemplates.put(promQueryTypeBodySet.getKey(), prometheusQueryTemplateFormatter.format(promQueryTypeBodySet.getValue()));
            }
            this.prometheusQueryTemplateProcessor = prometheusQueryTemplateProcessor;
        } catch (Exception e) {
            throw new OperationIncompleteException(e);
        }

    }

    @Override
    public PrometheusQueryRangeRequest getPrometheusServerRequest(PrometheusQueryConfiguration prometheusQueryConfiguration) throws OperationIncompleteException {
        try {
            String templateStr = prometheusQueryTemplates.get(prometheusQueryConfiguration.getQueryType());
            ObjectsUtils.requireNonNull(templateStr, new OperationIncompleteException("Can not find template by type " + prometheusQueryConfiguration.getQueryType()));

            String finalQuery = prometheusQueryTemplateProcessor.processTemplate(templateStr, prometheusQueryConfiguration);

            Boolean sslVerify = Boolean.TRUE;
            if (prometheusQueryConfiguration.getServerSSLVerify().equals("False") || prometheusQueryConfiguration.getServerSSLVerify().equals("false")) sslVerify = Boolean.FALSE;

            // Расчёт временных параметров
            long end = System.currentTimeMillis() / 1000; // сейчас (в секундах)
            long rangeSeconds = Long.parseLong(prometheusQueryConfiguration.getQueryTimeRange()) * 60; // допустим getQueryTimeRange(), в минутах
            long start = end - rangeSeconds;

            PrometheusQueryRangeRequest prometheusQueryRangeRequest = PrometheusQueryRangeRequest.newBuilder()
                    .setPrometheusQuery(finalQuery)
                    .setSslVerify(sslVerify)
                    .setStart(Instant.ofEpochSecond(start))
                    .setEnd(Instant.ofEpochSecond(end))
//                    .setStep(Duration.parse(prometheusQueryConfiguration.getQueryStep()))
                    .setStep(parseSeconds(prometheusQueryConfiguration.getQueryStep()))
                    .setUrl(new URL(prometheusQueryConfiguration.getServerURL()))
                    .build();

            logger.debug("PrometheusQueryRangeRequest is {}", prometheusQueryRangeRequest);
            return prometheusQueryRangeRequest;
        } catch (Exception e) {
            throw new OperationIncompleteException(e);
        }
    }

    public Duration parseSeconds(String input) {
        try {
            long seconds = Long.parseLong(input.trim());
            return Duration.ofSeconds(seconds);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format: " + input, e);
        }
    }

}
