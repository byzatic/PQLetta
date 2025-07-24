package io.github.byzatic.pqletta.client.impl.query_range.response_handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.byzatic.pqletta.client.impl.query_range.response_handler.dto.Response;
import io.github.byzatic.pqletta.client.impl.query_range.response_handler.dto.Value;
import io.github.byzatic.pqletta.client.impl.query_range.support.ValueDeserializer;

import java.io.InvalidObjectException;

class SupportPrometheusResponseUnmarshaller {
    private final static Logger logger = LoggerFactory.getLogger(SupportPrometheusResponseUnmarshaller.class);
    private final static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Value.class, new ValueDeserializer())
            .create();

    public static Response get(String responseBodyJson) throws InvalidObjectException {
        try {
            Response response = gson.fromJson(responseBodyJson, Response.class);
            logger.debug(response.toString());
            return response;
        } catch (Exception e) {
            throw new InvalidObjectException("Can't deserialize body: " + e.getMessage());
        }
    }
}
