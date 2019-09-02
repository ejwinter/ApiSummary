package com.winteredge.apisum

import spock.lang.Specification
import spock.lang.Subject

class ApiSummarizerMainSpec extends Specification {

    @Subject
    ApiSummarizerMain summerizer

    def setup(){
        summerizer = new ApiSummarizerMain()
    }

    def "something"(){
        when:
        summerizer.run()
        then:
        true
    }
}
