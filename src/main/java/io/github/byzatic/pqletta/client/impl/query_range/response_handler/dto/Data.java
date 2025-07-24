package io.github.byzatic.pqletta.client.impl.query_range.response_handler.dto;


import java.util.List;
import java.util.Objects;

public class Data {
    private String resultType;
    private List<Result> result;

    public String getResultType() {
        return resultType;
    }

    public List<Result> getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return Objects.equals(resultType, data.resultType) && Objects.equals(result, data.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resultType, result);
    }

    @Override
    public String toString() {
        return "Data{" +
                "resulttype='" + resultType + '\'' +
                ", result=" + result +
                '}';
    }
}
