package me.shib.lib.freshrelease.api;

import java.text.SimpleDateFormat;
import java.util.*;

public final class Query {

    private static transient final SimpleDateFormat queryValueFormatter;

    static {
        queryValueFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        queryValueFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private final transient Set<String> includes;
    private final transient Map<Condition, QueryItem> queryItemsMap;

    public Query() {
        this.includes = new HashSet<>();
        include(IncludeField.project);
        this.queryItemsMap = new HashMap<>();
    }

    private static String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    public Query include(IncludeField field) {
        includes.add(field.name());
        return this;
    }

    Query includeRequiredFields() {
        include(IncludeField.project);
        include(IncludeField.issue_type);
        include(IncludeField.owner);
        include(IncludeField.reporter);
        include(IncludeField.status);
        include(IncludeField.priority);
        return this;
    }

    public Query includeAllFields() {
        for (IncludeField field : IncludeField.values()) {
            include(field);
        }
        return this;
    }

    public Query add(Query.Condition condition, Query.Operator operator, List<Value> values) {
        QueryItem queryItem = new QueryItem(condition, operator, values);
        this.queryItemsMap.put(condition, queryItem);
        return this;
    }

    public Query add(Query.Condition condition, Query.Operator operator, Value... values) {
        if (values != null && values.length > 0) {
            return add(condition, operator, Arrays.asList(values));
        }
        return this;
    }

    public Query add(Query.Condition condition, Query.Operator operator, String... values) {
        if (values != null && values.length > 0) {
            List<Value> valueList = new ArrayList<>();
            for (String value : values) {
                valueList.add(new Value(value));
            }
            return add(condition, operator, valueList);
        }
        return this;
    }

    public Query add(Query.Condition condition, Query.Operator operator, Long... values) {
        if (values != null && values.length > 0) {
            List<Value> valueList = new ArrayList<>();
            for (Long value : values) {
                valueList.add(new Value(value));
            }
            return add(condition, operator, valueList);
        }
        return this;
    }

    public Query add(Query.Condition condition, Query.Operator operator, Date... values) {
        if (values != null && values.length > 0) {
            List<Value> valueList = new ArrayList<>();
            for (Date value : values) {
                valueList.add(new Value(value));
            }
            return add(condition, operator, valueList);
        }
        return this;
    }

    Map<String, String> toQueryMap() {
        Map<String, String> queryMap = new HashMap<>();
        List<QueryItem> queryItems = new ArrayList<>(queryItemsMap.values());
        for (int i = 0; i < queryItems.size(); i++) {
            queryMap.put("query_hash[" + i + "][condition]", queryItems.get(i).condition.toString());
            queryMap.put("query_hash[" + i + "][operator]", queryItems.get(i).operator.toString());
            queryMap.put("query_hash[" + i + "][value]", queryItems.get(i).valueStr());
        }
        queryMap.put("include", String.join(",", includes));
        return queryMap;
    }

    public enum IncludeField {
        tags, project, issue_type, owner, reporter, status, priority
    }

    public enum Condition {
        project_id("project_id"),
        owner_id("owner_id"),
        reporter_id("reporter_id"),
        status_id("status_id"),
        priority_id("priority_id"),
        issue_id("issue_id"),
        issue_type_id("issue_type_id"),
        tags("base_tags.name"),
        created_at("created_at"),
        updated_at("updated_at");

        private final String value;

        Condition(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum Operator {
        is,
        is_not,
        is_on,
        is_in,
        is_provided,
        is_not_provided,
        is_after,
        is_before,
        is_in_the_range,
        contains,
        does_not_contain,
        starts_with,
        ends_with,
        is_greater_than,
        is_less_than
    }

    public static class Value {

        String value;

        public Value(Date date) {
            this.value = queryValueFormatter.format(date);
        }

        public Value(Long value) {
            this.value = value + "";
        }

        public Value(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private class QueryItem {

        private final Condition condition;
        private final Operator operator;
        private List<Value> values;

        private QueryItem(Condition condition, Operator operator, Value value) {
            this(condition, operator, new ArrayList<>());
            this.values.add(value);
        }

        private QueryItem(Condition condition, Operator operator, List<Value> values) {
            this.condition = condition;
            this.operator = operator;
            this.values = values;
            if (this.values == null) {
                this.values = new ArrayList<>();
            }
        }

        private String valueStr() {
            StringBuilder valueStr = new StringBuilder();
            List<Value> valueList = new ArrayList<>(values);
            valueStr.append(valueList.get(0));
            for (int i = 1; i < valueList.size(); i++) {
                valueStr.append(",").append(valueList.get(i));
            }
            return valueStr.toString();
        }
    }
}
