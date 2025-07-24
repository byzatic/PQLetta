package io.github.byzatic.pqletta.client.dto.response.impl.success;


import java.util.List;
import java.util.Objects;

public class Data {
    private String resultType;
    private List<Result> result;

    public Data() {
    }

    private Data(Builder builder) {
        resultType = builder.resultType;
        result = builder.result;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Data copy) {
        Builder builder = new Builder();
        builder.resultType = copy.getResultType();
        builder.result = copy.getResult();
        return builder;
    }

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

    /**
     * {@code Data} builder static inner class.
     */
    public static final class Builder {
        private String resultType;
        private List<Result> result;

        private Builder() {
        }

        /**
         * Sets the {@code resultType} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param resultType the {@code resultType} to set
         * @return a reference to this Builder
         */
        public Builder setResultType(String resultType) {
            this.resultType = resultType;
            return this;
        }

        /**
         * Sets the {@code result} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param result the {@code result} to set
         * @return a reference to this Builder
         */
        public Builder setResult(List<Result> result) {
            this.result = result;
            return this;
        }

        /**
         * Returns a {@code Data} built from the parameters previously set.
         *
         * @return a {@code Data} built with parameters of this {@code Data.Builder}
         */
        public Data build() {
            return new Data(this);
        }
    }
}
