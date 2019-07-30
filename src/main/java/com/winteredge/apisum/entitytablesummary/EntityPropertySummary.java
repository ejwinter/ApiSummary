package com.winteredge.apisum.entitytablesummary;

public class EntityPropertySummary {

    private String name;

    private String type;

    private String description;

    private String example;

    private boolean required = false;

    public String getName() {
        return name;
    }

    public EntityPropertySummary setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public EntityPropertySummary setType(String type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EntityPropertySummary setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getExample() {
        return example;
    }

    public EntityPropertySummary setExample(String example) {
        this.example = example;
        return this;
    }

    public boolean isRequired() {
        return required;
    }

    public EntityPropertySummary setRequired(boolean required) {
        this.required = required;
        return this;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EntityPropertySummary{");
        sb.append("name='").append(name).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", example='").append(example).append('\'');
        sb.append(", required=").append(required);
        sb.append('}');
        return sb.toString();
    }
}
