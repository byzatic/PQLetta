package io.github.byzatic.pqletta.client.impl.query_range.support;

import com.google.gson.*;
import io.github.byzatic.pqletta.client.impl.query_range.response_handler.dto.Value;

import java.lang.reflect.Type;
import java.time.Instant;

public class ValueDeserializer implements JsonDeserializer<Value> {
    @Override
    public Value deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray array = json.getAsJsonArray();
        double timestamp = (double) array.get(0).getAsDouble();
        String valueStr = array.get(1).getAsString();

        Instant instant = convert(timestamp);

        return Value.newBuilder()
                .setInstantOfEpochSecond(instant)
                .setData(valueStr)
                .build();
    }

     Instant convert(double timestamp){
        long seconds =  Math.round(Math.floor((double) timestamp));
        long nanos = (long) ((timestamp - seconds) * 1_000_000_000);

        Instant instant = Instant.ofEpochSecond(seconds, nanos);

        return instant;
    }
}
