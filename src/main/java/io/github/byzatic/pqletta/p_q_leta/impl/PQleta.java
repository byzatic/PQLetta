package io.github.byzatic.pqletta.p_q_leta.impl;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.byzatic.commons.base_exceptions.OperationIncompleteException;
import io.github.byzatic.pqletta.client.PrometheusAPIClientInterface;
import io.github.byzatic.pqletta.client.dto.request.impl.PrometheusQueryRangeRequest;
import io.github.byzatic.pqletta.client.dto.response.PrometheusResponse;
import io.github.byzatic.pqletta.client.impl.PrometheusAPIClient;
import io.github.byzatic.pqletta.p_q_leta.PQletaInterface;
import io.github.byzatic.pqletta.p_q_leta.QueryDescription;
import io.github.byzatic.pqletta.p_q_leta.impl.configuration_dao.PromqlConfigurationDAO;
import io.github.byzatic.pqletta.p_q_leta.impl.local_dto.query_configuration.PrometheusQueryConfiguration;
import io.github.byzatic.pqletta.p_q_leta.impl.local_dto.server_request.PrometheusRequest;
import io.github.byzatic.pqletta.p_q_leta.impl.template_repository.PrometheusQueryTemplateFormatterInterface;
import io.github.byzatic.pqletta.p_q_leta.impl.template_repository.PrometheusQueryTemplateProcessorInterface;
import io.github.byzatic.pqletta.p_q_leta.impl.template_repository.PrometheusQueryTemplatesParserInterface;
import io.github.byzatic.pqletta.p_q_leta.impl.template_repository.PromqlTemplateRepository;
import io.github.byzatic.pqletta.p_q_leta.impl.template_repository.formatter.PrometheusQueryTemplateFormatter;
import io.github.byzatic.pqletta.p_q_leta.impl.template_repository.parser.PrometheusQueryTemplatesParser;
import io.github.byzatic.pqletta.p_q_leta.impl.template_repository.template_processor.PrometheusQueryTemplateProcessor;

import java.nio.file.Path;

public class PQleta implements PQletaInterface {
    private final static Logger logger = LoggerFactory.getLogger(PQleta.class);
    private final PromqlConfigurationDAOInterface promqlConfigurationDAO;
    private final PromqlTemplateRepositoryInterface promqlTemplateRepository;

    public PQleta(@NotNull Path queryParamsFilePath, @NotNull Path queryTemplatesFilePath) throws OperationIncompleteException {
        try {
            this.promqlConfigurationDAO = new PromqlConfigurationDAO(queryParamsFilePath);
            // TODO: PrometheusQueryTemplatesParserInterface PrometheusQueryTemplateFormatterInterface and PrometheusQueryTemplateProcessorInterface should not be there
            PrometheusQueryTemplatesParserInterface prometheusQueryTemplatesParser = new PrometheusQueryTemplatesParser();
            PrometheusQueryTemplateFormatterInterface prometheusQueryTemplateFormatter = new PrometheusQueryTemplateFormatter();
            PrometheusQueryTemplateProcessorInterface prometheusQueryTemplateProcessor = new PrometheusQueryTemplateProcessor();
            this.promqlTemplateRepository = new PromqlTemplateRepository(
                    prometheusQueryTemplatesParser,
                    prometheusQueryTemplateFormatter,
                    prometheusQueryTemplateProcessor,
                    queryTemplatesFilePath
            );
        } catch (Exception e) {
            throw new OperationIncompleteException(e);
        }
    }

    @Override
    @NotNull
    public PrometheusResponse processQuery(@NotNull QueryDescription queryDescription) throws OperationIncompleteException {
        try {
            logger.debug("Starts process query by description {}", queryDescription);

            PrometheusQueryConfiguration prometheusQueryConfiguration = promqlConfigurationDAO.get(
                    PrometheusRequest.newBuilder()
                            .setPrometheusQueryId(queryDescription.getQueryIdentifier())
                            .build()
            );
            logger.debug("Created prometheus query configuration {}", prometheusQueryConfiguration);

            PrometheusQueryRangeRequest prometheusQueryRangeRequest = promqlTemplateRepository.getPrometheusServerRequest(prometheusQueryConfiguration);

            logger.debug("Created prometheus server request {}", prometheusQueryRangeRequest);

            PrometheusAPIClientInterface prometheusAPIClient = PrometheusAPIClient.newBuilder()
                    .setServerUrl(prometheusQueryRangeRequest.getUrl())
                    .setSslVerify(prometheusQueryRangeRequest.getSslVerify())
                    .build();

            logger.debug("Created prometheus API client");

            PrometheusResponse prometheusResult = prometheusAPIClient.queryRange(
                    prometheusQueryRangeRequest.getPrometheusQuery(),
                    prometheusQueryRangeRequest.getStart(),
                    prometheusQueryRangeRequest.getEnd(),
                    prometheusQueryRangeRequest.getStep()
            );

            logger.debug("Returns prometheus result {}", prometheusResult);

            return prometheusResult;

        } catch (Exception e) {
            throw new OperationIncompleteException(e);
        }
    }
}
