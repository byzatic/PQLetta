package io.github.byzatic.pqletta.p_q_leta.impl.configuration_dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ibm.icu.impl.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.byzatic.commons.base_exceptions.OperationIncompleteException;
import io.github.byzatic.pqletta.p_q_leta.impl.PromqlConfigurationDAOInterface;
import io.github.byzatic.pqletta.p_q_leta.impl.configuration_dao.dto.Label;
import io.github.byzatic.pqletta.p_q_leta.impl.configuration_dao.dto.QueryConfiguration;
import io.github.byzatic.pqletta.p_q_leta.impl.configuration_dao.dto.QueryDescription;
import io.github.byzatic.pqletta.p_q_leta.impl.configuration_dao.dto.ServerDescription;
import io.github.byzatic.pqletta.p_q_leta.impl.local_dto.query_configuration.PrometheusQueryConfiguration;
import io.github.byzatic.pqletta.p_q_leta.impl.local_dto.query_configuration.QueryLabel;
import io.github.byzatic.pqletta.p_q_leta.impl.local_dto.query_configuration.QueryParameter;
import io.github.byzatic.pqletta.p_q_leta.impl.local_dto.server_request.PrometheusRequest;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PromqlConfigurationDAO implements PromqlConfigurationDAOInterface {
    private final static Logger logger = LoggerFactory.getLogger(PromqlConfigurationDAO.class);
    private final Gson gson = new Gson();
    private final List<QueryConfiguration> queryConfigurationList;

    public PromqlConfigurationDAO(Path configFilePath) throws OperationIncompleteException {
        queryConfigurationList = load(configFilePath);
    }

    private List<QueryConfiguration> load(Path configFilePath) throws OperationIncompleteException {
        try {
            FileReader reader = new FileReader(configFilePath.toFile());
            Type listType = new TypeToken<List<QueryConfiguration>>(){}.getType();
            List<QueryConfiguration> configs = gson.fromJson(reader, listType);

            for (QueryConfiguration config : configs) {
                logger.debug("Server: " + config.getServerDescription().getUrl());
                for (QueryDescription query : config.getQueryDescriptions()) {
                    logger.debug("Type: " + query.getType());
                    logger.debug("Identifier: " + query.getIdentifier());
                    for (Label label : query.getLabels()) {
                        logger.debug("Label: " + label.getKey() + label.getSign() + label.getValue());
                    }
                }
            }

            return configs;
        } catch (Exception e) {
            throw new OperationIncompleteException(e);
        }
    }

    @Override
    public PrometheusQueryConfiguration get(PrometheusRequest prometheusRequest) throws OperationIncompleteException {
        Pair<ServerDescription, QueryDescription> serverDescriptionQueryDescriptionPair = search(prometheusRequest);

        List<QueryParameter> queryParameters = new ArrayList<>();
        queryParameters.add(QueryParameter.newBuilder().setPlaceholder("LLIMITPLACEHOLDER").setReplacement(serverDescriptionQueryDescriptionPair.second.getLowerLimit()).build());
        queryParameters.add(QueryParameter.newBuilder().setPlaceholder("HLIMITPLACEHOLDER").setReplacement(serverDescriptionQueryDescriptionPair.second.getUpperLimit()).build());

        List<QueryLabel> queryLabels = new ArrayList<>();
        for (Label label : serverDescriptionQueryDescriptionPair.second.getLabels()) {
            queryLabels.add(QueryLabel.newBuilder().setKey(label.getKey()).setValue(label.getValue()).setSign(label.getSign()).build());
        }

        PrometheusQueryConfiguration prometheusQueryConfiguration = PrometheusQueryConfiguration.newBuilder()
                .setQueryIdentifier(prometheusRequest.getPrometheusQueryId())
                .setQueryType(serverDescriptionQueryDescriptionPair.second.getType())
                .setServerURL(serverDescriptionQueryDescriptionPair.first.getUrl())
                .setServerSSLVerify(serverDescriptionQueryDescriptionPair.first.getSslVerify())
                .setQueryStep(serverDescriptionQueryDescriptionPair.second.getStep())
                .setQueryTimeRange(serverDescriptionQueryDescriptionPair.second.getTimeRange())
                .setQueryLabels(queryLabels)
                .setQueryParameters(queryParameters)
                .build();
        logger.debug("Created PrometheusQueryConfiguration -> {}", prometheusQueryConfiguration);
        return prometheusQueryConfiguration;
    }

    private Pair<ServerDescription, QueryDescription> search(PrometheusRequest prometheusRequest) throws OperationIncompleteException {
        Pair<ServerDescription, QueryDescription> serverDescriptionQueryDescriptionPair = null;
        for (QueryConfiguration queryConfiguration : this.queryConfigurationList) {
            for (QueryDescription queryDescription : queryConfiguration.getQueryDescriptions()) {
                if (queryDescription.getIdentifier().equals(prometheusRequest.getPrometheusQueryId())) {
                    serverDescriptionQueryDescriptionPair = Pair.of(queryConfiguration.getServerDescription(), queryDescription);
                }
            }
        }
        if (serverDescriptionQueryDescriptionPair == null) throw new OperationIncompleteException("Can't find Pair<ServerDescription, QueryDescription> by "+ prometheusRequest);
        return serverDescriptionQueryDescriptionPair;
    }

}
