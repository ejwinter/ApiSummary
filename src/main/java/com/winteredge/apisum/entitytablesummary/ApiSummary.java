package com.winteredge.apisum.entitytablesummary;

import java.util.List;
import java.util.Map;

/**
 * A high level summary of an API.  It has information about the API as well as the entities and end points of that API.
 */
public class ApiSummary {
    private final String title;
    private final String description;
    private final Map<String, EntitySummary> entitySummaries;
    private List<EndpointSummary> endpointSummaries;

    public ApiSummary(String title, String description, Map<String, EntitySummary> entitySummaries) {
        this.title = title;
        this.description = description;
        this.entitySummaries = entitySummaries;
    }

    public Map<String, EntitySummary> getEntitySummaries() {
        return entitySummaries;
    }

    public List<EndpointSummary> getEndpointSummaries() {
        return endpointSummaries;
    }

    public ApiSummary setEndpointSummaries(List<EndpointSummary> endpointSummaries) {
        this.endpointSummaries = endpointSummaries;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
