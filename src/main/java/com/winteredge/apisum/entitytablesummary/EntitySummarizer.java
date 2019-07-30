package com.winteredge.apisum.entitytablesummary;

import com.reprezen.kaizen.oasparser.OpenApi3Parser;
import com.reprezen.kaizen.oasparser.model3.OpenApi3;
import com.reprezen.kaizen.oasparser.model3.Schema;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySummarizer {

    public List<EntitySummary> summarize(File openApiFile) throws Exception {

        OpenApi3 openApi = new  OpenApi3Parser().parse(openApiFile);
        return openApi.getSchemas().entrySet().stream()
                .map(entry -> summarizeEntity(entry.getKey(), entry.getValue()))
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    private EntitySummary summarizeEntity(String name, Schema schema) {

        List<EntityPropertySummary> properties = schema.getProperties().entrySet().stream()
                .map(entry -> summarizeProperty(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return new EntitySummary(name, schema.getDescription(), properties);
    }

    private EntityPropertySummary summarizeProperty(String key, Schema schema) {
        EntityPropertySummary summary = new EntityPropertySummary()
                .setName(key)
                .setDescription(schema.getDescription())
                .setRequired(schema.hasRequiredFields())
                .setType(schema.getType());

        if(schema.getExample() != null){
            summary.setExample(schema.getExample().toString());
        }
        return summary;
    }
}
