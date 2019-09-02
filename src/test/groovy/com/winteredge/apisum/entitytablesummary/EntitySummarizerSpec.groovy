package com.winteredge.apisum.entitytablesummary

import spock.lang.Specification
import spock.lang.Subject

class EntitySummarizerSpec extends Specification {

    @Subject
    EntitySummarizer summarizer

    def setup(){
        summarizer = new EntitySummarizer()
    }

    def "summarize"(){

        when:
        ApiSummary summary = summarizer.summarize(new File(EntitySummarizer.class.getClassLoader().getResource("open-api.yml").toURI()))

        then:
        summary.entitySummaries['BreakendCoordinate'].description == 'A coordinate that has a confidence interval start position where a break occurs and another break from somewhere else in the genome that fuses with it.'

        summary.entitySummaries['BreakendCoordinate'].properties.size() == 6
    }
}
