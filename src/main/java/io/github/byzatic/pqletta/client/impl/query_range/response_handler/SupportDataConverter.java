package io.github.byzatic.pqletta.client.impl.query_range.response_handler;

import org.jetbrains.annotations.NotNull;
import io.github.byzatic.pqletta.client.dto.response.impl.success.Data;
import io.github.byzatic.pqletta.client.dto.response.impl.success.Result;
import io.github.byzatic.pqletta.client.dto.response.impl.success.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SupportDataConverter {

    public static Data convert(@NotNull io.github.byzatic.pqletta.client.impl.query_range.response_handler.dto.Data data) {

        List<Result> resultList = new ArrayList<>();

        for (io.github.byzatic.pqletta.client.impl.query_range.response_handler.dto.Result result : data.getResult()) {
            List<Value> resultValues = new ArrayList<>();
            for (io.github.byzatic.pqletta.client.impl.query_range.response_handler.dto.Value value : result.getValues()) {
                resultValues.add(
                        Value.newBuilder()
                                .setData(value.getData())
                                .setInstantOfEpochSecond(value.getInstantOfEpochSecond())
                                .build()
                );
            }
            resultList.add(
                    Result.newBuilder()
                            .setMetric(new HashMap<>(result.getMetric()))
                            .setValues(resultValues)
                            .build()
            );
        }

        Data newData = Data.newBuilder()
                .setResult(resultList)
                .setResultType(data.getResultType())
                .build();

        return newData;
    }
}
