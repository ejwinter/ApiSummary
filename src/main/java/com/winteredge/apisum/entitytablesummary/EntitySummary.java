package com.winteredge.apisum.entitytablesummary;

import java.util.List;

public class EntitySummary {

    public String name;

    public String description;

    public String example;

    public List<EntityPropertySummary> properties;

    public EntitySummary(String name, String description, List<EntityPropertySummary> properties) {
        this.name = name;
        this.description = description;
        this.properties = properties;
    }

    public EntitySummary() {
    }

    public String getName() {
        return name;
    }

    public EntitySummary setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EntitySummary setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<EntityPropertySummary> getProperties() {
        return properties;
    }

    public EntitySummary setProperties(List<EntityPropertySummary> properties) {
        this.properties = properties;
        return this;
    }

    public String getExample() {
        return example;
    }

    public EntitySummary setExample(String example) {
        this.example = example;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EntitySummary{");
        sb.append("name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", example='").append(example).append('\'');
        sb.append(", properties=").append(properties);
        sb.append('}');
        return sb.toString();
    }
}
