package com.winteredge.apisum.entitytablesummary;

public class EndpointSummary {

    private String description;

    private String method;

    private String url;

    public EndpointSummary(String description, String method, String url) {
        this.description = description;
        this.method = method;
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }
}
