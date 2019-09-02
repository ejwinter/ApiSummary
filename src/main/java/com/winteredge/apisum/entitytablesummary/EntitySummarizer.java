package com.winteredge.apisum.entitytablesummary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.reprezen.kaizen.oasparser.OpenApi3Parser;
import com.reprezen.kaizen.oasparser.model3.OpenApi3;
import com.reprezen.kaizen.oasparser.model3.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EntitySummarizer {

    private static final Logger logger = LoggerFactory.getLogger(EntitySummarizer.class);

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    private final OpenApi3Parser openApi3Parser = new OpenApi3Parser();

    public ApiSummary summarize(File openApiFile) throws Exception {
        OpenApi3 openApi = openApi3Parser.parse(openApiFile);
        LinkedHashMap<String, EntitySummary> summaries = openApi.getSchemas().entrySet().stream()
                .map(entry -> summarizeEntity(entry.getKey(), entry.getValue()))
                .collect(Collectors.toMap(EntitySummary::getName, s -> s, (v1, v2) -> v2, LinkedHashMap::new));
        fleshoutParentProperties(summaries, summaries);
        return new ApiSummary(openApi.getInfo().getTitle(), openApi.getInfo().getDescription(), summaries);
    }

    private void fleshoutParentProperties(Map<String, EntitySummary> summaries, Map<String, EntitySummary> topLevel) {
        if(summaries == null || summaries.isEmpty()){
            return;
        }

        for (EntitySummary entitySummary : summaries.values()) {
            Map<String, EntitySummary> allProperties = new HashMap<>(entitySummary.getProperties());
            entitySummary.getParentRefs().stream()
                    .flatMap(parent -> topLevel.get(parent).getProperties().entrySet().stream())
                    .forEach(parentProperty -> allProperties.put(parentProperty.getKey(), parentProperty.getValue()));
            entitySummary.setProperties(allProperties);
        }


        summaries.values().stream()
                .map(EntitySummary::getProperties)
                .forEach(summaryProperties -> {
                    fleshoutParentProperties(summaryProperties, topLevel);
                });
    }

    private EntitySummary summarizeEntity(String name, Schema schema) {

        if(schema.getProperties().isEmpty()){
            return summarizeProperty(schema);
        }

        Map<String, EntitySummary> properties = schema.getProperties().entrySet().stream()
                .collect(Collectors.toMap(e->e.getKey(), e-> summarizeProperty(e.getValue())));

        EntitySummary entitySummary = new EntitySummary(name, schema.getDescription(), properties);
        if(schema.getExample() != null){
            entitySummary.setExample(schema.getExample().toString());
        }
        if(schema.getAllOfSchemas() != null){
            entitySummary.setParentRefs(schema.getAllOfSchemas().stream().map(Schema::getName).collect(Collectors.toList()));
        }
        return entitySummary;
    }

    private EntitySummary summarizeProperty(Schema schema) {
        EntitySummary summary = new EntitySummary()
                .setName(schema.getName())
                .setDescription(schema.getDescription())
                .setType(schema.getType());

        if(schema.hasRequiredFields()){
            summary.setRequired(schema.hasRequiredFields());
        }

        if(schema.getExample() != null){
            summary.setExample(schema.getExample().toString());
        }

        Map<String, EntitySummary> properties = new HashMap<>();
        if(schema.getAdditionalPropertiesSchema()!=null && schema.getAdditionalPropertiesSchema().getName() != null){
            summary.setType(String.format("Dictionary<string,%s>", schema.getAdditionalPropertiesSchema().getName()));
            Map<String, EntitySummary> propertySummaries = schema.getAdditionalPropertiesSchema().getProperties().values().stream()
                    .collect(Collectors.toMap(p -> p.getName(), p -> summarizeProperty(p)));
            properties.putAll(propertySummaries);
        }

        if ("array".equals(summary.getType())) {
            if(schema.getItemsSchema().getType() != null){
                summary.setType(String.format("%s[]", schema.getItemsSchema().getType()));
            }else if(schema.getItemsSchema().getName() != null){
                summary.setType(String.format("%s[]", schema.getItemsSchema().getName()));
            }else{
                summary.setType("[]");
            }

            if(!schema.getItemsSchema().getProperties().isEmpty()){
                Map<String, EntitySummary> propertySummaries = schema.getItemsSchema().getProperties().values().stream()
                        .collect(Collectors.toMap(p -> p.getName(), p -> summarizeProperty(p)));
                properties.putAll(propertySummaries);
            }
        }

        if(schema.getProperties() != null){
            properties.putAll(schema.getProperties().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e->summarizeProperty(e.getValue()))));

        }

        if(schema.getAllOfSchemas() != null){
            summary.setParentRefs(schema.getAllOfSchemas().stream().map(Schema::getName).collect(Collectors.toList()));
        }
        summary.setProperties(properties);

        return summary;
    }
}
