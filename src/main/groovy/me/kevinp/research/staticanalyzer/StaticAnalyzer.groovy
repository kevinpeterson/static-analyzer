package me.kevinp.research.staticanalyzer

import groovyx.net.http.ContentType
import me.kevinp.research.staticanalyzer.maven.DependencyChecker
import me.kevinp.research.staticanalyzer.maven.JarInspector
import me.kevinp.research.staticanalyzer.maven.MavenCentralCrawler
import me.kevinp.research.staticanalyzer.maven.MavenCentralDownloader
import me.kevinp.research.staticanalyzer.metrics.MetricsProcessor
import me.kevinp.research.staticanalyzer.model.Artifact
import me.kevinp.research.staticanalyzer.model.ClassMetrics
import me.kevinp.research.staticanalyzer.repository.ArtifactRepository
import org.springframework.stereotype.Component

import javax.annotation.Resource

@Component
class StaticAnalyzer {

    @Resource
    MavenCentralCrawler crawler

    @Resource
    MavenCentralDownloader downloader

    @Resource
    JarInspector inspector

    @Resource
    MetricsProcessor processor

    @Resource
    ArtifactRepository repository

    @Resource
    DependencyChecker dependencyChecker

    void analyze(){
        crawler.crawl(
            {  json ->
                downloader.download(json.g, json.a, json.v, 'jar', ContentType.BINARY,
                        { file ->
                            inspector.unzip(file, {
                                classes ->
                                    def artifact = new Artifact()
                                    artifact.groupId = json.g
                                    artifact.artifactId = json.a
                                    artifact.versionId = json.v

                                    processor.process(classes, addClassMetrics(artifact))

                                    downloader.download(json.g, json.a, json.v, 'pom', ContentType.TEXT,
                                            { pom ->
                                                dependencyChecker.getDependencies(pom).each { artifact.addDependency(it) }
                                            })

                                    repository.save(artifact)
                            })
                        })
            }
        )
    }

    def addClassMetrics(artifact){
        {clazz, classMetrics ->
            def metrics = new ClassMetrics()

            metrics.className = clazz
            metrics.dit = classMetrics.dit
            metrics.noc = classMetrics.noc

            artifact.addClassMetrics(metrics)
        }
    }
}
