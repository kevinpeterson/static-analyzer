package me.kevinp.research.staticanalyzer.maven

import groovyx.net.http.*
import org.springframework.stereotype.Component

@Component
class MavenCentralCrawler {

    void crawl(resultClosure){
        def moreResults = true
        def pageSize = 50
        def position = 0

        def http = new HTTPBuilder( 'http://search.maven.org/solrsearch/select' )

        while(moreResults){
            http.get( query: [
                    q:'p:"jar"',
                    wt:'json',
                    core:'gav',
                    start:position,
                    rows:pageSize
            ] ) { resp, json ->
                if(json.response.docs.length == 0){
                    moreResults = false
                } else {
                    json.response.docs.each {
                        resultClosure(it)
                    }

                    position += pageSize
                }
            }
        }
    }
}
