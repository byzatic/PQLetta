package io.github.byzatic.pqletta.p_q_leta.impl.local_dto.query_configuration;

import java.util.Objects;

public class QueryParameter {
    private String placeholder = null;
    private String replacement = null;

    private QueryParameter() {
    }

    private QueryParameter(Builder builder) {
        placeholder = builder.placeholder;
        replacement = builder.replacement;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(QueryParameter copy) {
        Builder builder = new Builder();
        builder.placeholder = copy.getPlaceholder();
        builder.replacement = copy.getReplacement();
        return builder;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getReplacement() {
        return replacement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryParameter that = (QueryParameter) o;
        return Objects.equals(placeholder, that.placeholder) && Objects.equals(replacement, that.replacement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeholder, replacement);
    }

    @Override
    public String toString() {
        return "QueryParameter{" +
                "placeholder='" + placeholder + '\'' +
                ", replacement='" + replacement + '\'' +
                '}';
    }

    /**
     * {@code QueryParameter} builder static inner class.
     */
    public static final class Builder {
        private String placeholder;
        private String replacement;

        private Builder() {
        }

        /**
         * Sets the {@code placeholder} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param placeholder the {@code placeholder} to set
         * @return a reference to this Builder
         */
        public Builder setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        /**
         * Sets the {@code replacement} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param replacement the {@code replacement} to set
         * @return a reference to this Builder
         */
        public Builder setReplacement(String replacement) {
            this.replacement = replacement;
            return this;
        }

        /**
         * Returns a {@code QueryParameter} built from the parameters previously set.
         *
         * @return a {@code QueryParameter} built with parameters of this {@code QueryParameter.Builder}
         */
        public QueryParameter build() {
            return new QueryParameter(this);
        }
    }
}
