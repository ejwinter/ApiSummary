package com.winteredge.apisum.entitytablesummary;

import java.util.Map;

/**
 * A high level summary of an API.  It has information about the API as well as the entities and end points of that API.
 */
public class ApiSummary {
    private final Map<String, EntitySummary> entitySummaries;

    public ApiSummary(Map<String, EntitySummary> entitySummaries) {
        this.entitySummaries = entitySummaries;
    }

    public Map<String, EntitySummary> getEntitySummaries() {
        return entitySummaries;
    }
}
