package me.kevinp.research.staticanalyzer.maven

import groovyx.net.http.HTTPBuilder
import org.apache.commons.io.FileUtils
import org.springframework.stereotype.Component

@Component
class MavenCentralDownloader {

    void download(groupId, artifactId, versionId, extension, contentType, resultClosure){
        def artifactPath = artifactId
        def groupPath = groupId.replace('.', '/');

        def path = groupPath + '/' + artifactPath + '/' + versionId + '/' + artifactPath + '-' + versionId + '.' + extension

        def httpBuilder = new HTTPBuilder( 'http://search.maven.org/remotecontent' )
        httpBuilder.handler.failure = { resp ->
            println "Unexpected failure: ${resp.statusLine} getting ${path}"
        }

        httpBuilder.get(query: [ filepath:path ], contentType : contentType) {
            resp, inputSream ->
                def tempFile = File.createTempFile(UUID.randomUUID().toString(), "." + extension)
                tempFile.withOutputStream { out -> out << inputSream }

                resultClosure(tempFile)

                FileUtils.forceDelete(tempFile)
        }

    }
}
