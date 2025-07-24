package io.github.byzatic.pqletta.p_q_leta.impl.local_dto.query_configuration;

import java.util.Objects;

public class QueryLabel {
    private String key = null;
    private String value = null;
    private String sign = null;

    private QueryLabel() {
    }

    private QueryLabel(Builder builder) {
        key = builder.key;
        value = builder.value;
        sign = builder.sign;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(QueryLabel copy) {
        Builder builder = new Builder();
        builder.key = copy.getKey();
        builder.value = copy.getValue();
        builder.sign = copy.getSign();
        return builder;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryLabel that = (QueryLabel) o;
        return Objects.equals(key, that.key) && Objects.equals(value, that.value) && Objects.equals(sign, that.sign);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value, sign);
    }

    @Override
    public String toString() {
        return "QueryLabel{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    /**
     * {@code QueryLabel} builder static inner class.
     */
    public static final class Builder {
        private String key;
        private String value;
        private String sign;

        private Builder() {
        }

        /**
         * Sets the {@code key} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param key the {@code key} to set
         * @return a reference to this Builder
         */
        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        /**
         * Sets the {@code value} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param value the {@code value} to set
         * @return a reference to this Builder
         */
        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        /**
         * Sets the {@code sign} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param sign the {@code sign} to set
         * @return a reference to this Builder
         */
        public Builder setSign(String sign) {
            this.sign = sign;
            return this;
        }

        /**
         * Returns a {@code QueryLabel} built from the parameters previously set.
         *
         * @return a {@code QueryLabel} built with parameters of this {@code QueryLabel.Builder}
         */
        public QueryLabel build() {
            return new QueryLabel(this);
        }
    }
}
