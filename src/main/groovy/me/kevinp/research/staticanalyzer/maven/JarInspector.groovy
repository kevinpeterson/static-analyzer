package me.kevinp.research.staticanalyzer.maven

import groovy.io.FileType
import org.apache.commons.io.FileUtils
import org.springframework.stereotype.Component

@Component
class JarInspector {

    void unzip( file, closure ){
        def builder = new AntBuilder()

        def tempDir = File.createTempFile('temp-', UUID.randomUUID().toString())
        tempDir.delete()
        tempDir.mkdir()

        builder.unzip(src:file.absolutePath, dest:tempDir.absolutePath, overwrite:false)

        def classList = []
        tempDir.eachFileRecurse (FileType.FILES) {
            classFile ->
                def path = classFile.absolutePath
                if( path ==~ /^.+?\.class$/ ) classList << classFile.absolutePath
        }

        closure(classList)

        FileUtils.deleteDirectory(tempDir)
    }

}
