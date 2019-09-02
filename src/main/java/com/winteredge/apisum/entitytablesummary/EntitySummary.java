package com.winteredge.apisum.entitytablesummary;

import java.util.*;

public class EntitySummary implements Comparable<EntitySummary>{

    private String name;

    private String description;

    private String example;

    private String type = "object";

    private String ref;

    private SortedMap<String, EntitySummary> properties;

    private boolean required = false;

    private List<String> parentRefs;

    public EntitySummary(String name, String description, Map<String, EntitySummary> properties) {
        this.name = name;
        this.description = description;
        this.properties = new TreeMap<>(properties);
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

    public SortedMap<String, EntitySummary> getProperties() {
        return properties;
    }

    public EntitySummary setProperties(Map<String,EntitySummary> properties) {
        this.properties = new TreeMap<>(properties);
        return this;
    }

    public String getExample() {
        return example;
    }

    public EntitySummary setExample(String example) {
        this.example = example;
        return this;
    }

    public String getType() {
        return type;
    }

    public EntitySummary setType(String type) {
        this.type = type;
        return this;
    }

    public boolean isRequired() {
        return required;
    }

    public EntitySummary setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public String getRef() {
        return ref;
    }

    public EntitySummary setRef(String ref) {
        this.ref = ref;
        return this;
    }

    public List<String> getParentRefs() {
        return parentRefs;
    }

    public EntitySummary setParentRefs(List<String> parentRefs) {
        this.parentRefs = parentRefs;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EntitySummary{");
        sb.append("name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", example='").append(example).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntitySummary that = (EntitySummary) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(example, that.example) &&
                Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, example, properties);
    }


    @Override
    public int compareTo(EntitySummary that) {
        if (this.name.compareTo(that.name) < 0) {
            return -1;
        } else if (this.name.compareTo(that.name) > 0) {
            return 1;
        }
        return 0;
    }
}
