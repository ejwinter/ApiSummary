package com.winteredge.apisum;

import freemarker.cache.StringTemplateLoader;
import freemarker.core.HTMLOutputFormat;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;

public class TemplateResolver {

    private final Template template;

    private static final Configuration configuration;
    static {
        configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setTemplateLoader(new StringTemplateLoader());
        configuration.setOutputFormat(HTMLOutputFormat.INSTANCE);

        BeansWrapperBuilder beansWrapperBuilder = new BeansWrapperBuilder(Configuration.VERSION_2_3_28);
        beansWrapperBuilder.setExposeFields(true);
        beansWrapperBuilder.setSimpleMapWrapper(true);
        configuration.setObjectWrapper(beansWrapperBuilder.build());
    }

    public TemplateResolver(String name, Reader templateReader) throws IOException {
        template = new Template(name, templateReader, configuration);
    }

    public void resolve(Object model, Writer output) throws IOException, TemplateException {
        template.process(model, output);
    }

    public String resolveToString(Object model) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        resolve(model, stringWriter);
        return stringWriter.toString();
    }
}
