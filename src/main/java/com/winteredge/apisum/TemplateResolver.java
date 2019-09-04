package com.winteredge.apisum;

import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.core.HTMLOutputFormat;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.logging.log4j.core.util.IOUtils;

import java.io.*;

public class TemplateResolver {

    private final Template template;

    private static final Configuration configuration;
    static {
        configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setOutputFormat(HTMLOutputFormat.INSTANCE);
        configuration.setTemplateLoader(new HtmlTemplateLoader(new StringTemplateLoader()));
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

    private static class HtmlTemplateLoader implements TemplateLoader{

        private final TemplateLoader delegate;

        public HtmlTemplateLoader(TemplateLoader delegate) {
            this.delegate = delegate;
        }

        @Override
        public Reader getReader(Object templateSource, String encoding) throws IOException {
            try(Reader reader = delegate.getReader(templateSource, encoding)){
                String templateText = IOUtils.toString(reader);
                //TODO: this isn't getting called for some reason...  I'm not sure how to
                return new StringReader("<#ftl strip_whitespace=false><#escape x as x?html?replace('\n', '<br>')>" + templateText + "</#escape>");
            }
        }

        @Override
        public Object findTemplateSource(String name) throws IOException {
            return delegate.findTemplateSource(name);
        }

        @Override
        public long getLastModified(Object templateSource) {
            return delegate.getLastModified(templateSource);
        }

        @Override
        public void closeTemplateSource(Object templateSource) throws IOException {
            delegate.closeTemplateSource(templateSource);
        }
    }
}
