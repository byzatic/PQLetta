package io.github.byzatic.pqletta.client.dto.response.impl.success;

import java.time.Instant;
import java.util.Objects;

public class Value {
    private Instant instantOfEpochSecond;
    private String data;

    public Value() {
    }

    private Value(Builder builder) {
        instantOfEpochSecond = builder.instantOfEpochSecond;
        data = builder.data;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Value copy) {
        Builder builder = new Builder();
        builder.instantOfEpochSecond = copy.getInstantOfEpochSecond();
        builder.data = copy.getData();
        return builder;
    }

    public Instant getInstantOfEpochSecond() {
        return instantOfEpochSecond;
    }

    public String getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Value value = (Value) o;
        return Objects.equals(instantOfEpochSecond, value.instantOfEpochSecond) && Objects.equals(data, value.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instantOfEpochSecond, data);
    }

    @Override
    public String toString() {
        return "Value{" +
                "instantOfEpochSecond=" + instantOfEpochSecond +
                ", data='" + data + '\'' +
                '}';
    }

    /**
     * {@code Value} builder static inner class.
     */
    public static final class Builder {
        private Instant instantOfEpochSecond;
        private String data;

        private Builder() {
        }

        /**
         * Sets the {@code instantOfEpochSecond} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param instantOfEpochSecond the {@code instantOfEpochSecond} to set
         * @return a reference to this Builder
         */
        public Builder setInstantOfEpochSecond(Instant instantOfEpochSecond) {
            this.instantOfEpochSecond = instantOfEpochSecond;
            return this;
        }

        /**
         * Sets the {@code data} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param data the {@code data} to set
         * @return a reference to this Builder
         */
        public Builder setData(String data) {
            this.data = data;
            return this;
        }

        /**
         * Returns a {@code Value} built from the parameters previously set.
         *
         * @return a {@code Value} built with parameters of this {@code Value.Builder}
         */
        public Value build() {
            return new Value(this);
        }
    }
}
