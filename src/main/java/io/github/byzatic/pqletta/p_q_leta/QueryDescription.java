package io.github.byzatic.pqletta.p_q_leta;

import java.util.Objects;

public class QueryDescription {
    private String queryIdentifier;

    public QueryDescription() {
    }

    private QueryDescription(Builder builder) {
        queryIdentifier = builder.queryIdentifier;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(QueryDescription copy) {
        Builder builder = new Builder();
        builder.queryIdentifier = copy.getQueryIdentifier();
        return builder;
    }

    public String getQueryIdentifier() {
        return queryIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryDescription that = (QueryDescription) o;
        return Objects.equals(queryIdentifier, that.queryIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryIdentifier);
    }

    @Override
    public String toString() {
        return "QueryDescription{" +
                "queryIdentifier='" + queryIdentifier + '\'' +
                '}';
    }

    /**
     * {@code QueryDescription} builder static inner class.
     */
    public static final class Builder {
        private String queryIdentifier;

        private Builder() {
        }

        /**
         * Sets the {@code queryIdentifier} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param queryIdentifier the {@code queryIdentifier} to set
         * @return a reference to this Builder
         */
        public Builder setQueryIdentifier(String queryIdentifier) {
            this.queryIdentifier = queryIdentifier;
            return this;
        }

        /**
         * Returns a {@code QueryDescription} built from the parameters previously set.
         *
         * @return a {@code QueryDescription} built with parameters of this {@code QueryDescription.Builder}
         */
        public QueryDescription build() {
            return new QueryDescription(this);
        }
    }
}
