package com.winteredge.apisum

import com.winteredge.apisum.entitytablesummary.ApiSummary
import com.winteredge.apisum.entitytablesummary.EntitySummarizer
import com.winteredge.apisum.entitytablesummary.EntitySummary
import spock.lang.Specification
import spock.lang.Subject

import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.ClipboardOwner
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.Transferable

class TemplateResolverSpec extends Specification implements ClipboardOwner {

    @Subject
    TemplateResolver templateResolver;

    def "resolveToString"(){

        given:
        templateResolver = new TemplateResolver("template", new InputStreamReader(getClass().getResourceAsStream("/listTemplate.ftl")))

        and:
        Map<String, List<String>> model = ["items": ["one", "two", "three"]]

        when:
        String resolved = templateResolver.resolveToString(model)

        then:
        resolved.contains("<li>two</li>");
    }

    def "resolveToString summaryized api"(){
        given:
        templateResolver = new TemplateResolver("template", new InputStreamReader(getClass().getResourceAsStream("/summaryTemplate.ftl")))

        and:
        Map<String, List<EntitySummary>> model = ["summaries":[
                new EntitySummary("name", "description", [
                        new EntitySummary("p1", "d1", []).setExample("e1").setType("string"),
                        new EntitySummary("p2", "d2", [new EntitySummary("p2-s1", "d2", []).setExample("eg").setType("string")]).setExample("e1").setType("object")
                ])
        ]]

        when:
        String resolved = templateResolver.resolveToString(model)

        then:
        println resolved
    }

    def "resolveToString summaryized mxd api"(){
        given:
        templateResolver = new TemplateResolver("template", new InputStreamReader(getClass().getResourceAsStream("/summaryTemplate.ftl")))

        and:
        EntitySummarizer summarizer = new EntitySummarizer()
        ApiSummary apiSummary = summarizer.summarize(new File(EntitySummarizer.class.getClassLoader().getResource("open-api.yml").toURI()))

        when:
        String resolved = templateResolver.resolveToString(["root":apiSummary])

        then:
        Toolkit.defaultToolkit.getSystemClipboard().setContents(new StringSelection(resolved), this);
    }

    @Override
    void lostOwnership(Clipboard clipboard, Transferable contents) {

    }
}
