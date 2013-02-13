package me.kevinp.research.staticanalyzer

import me.kevinp.research.staticanalyzer.maven.JarInspector
import me.kevinp.research.staticanalyzer.maven.MavenCentralCrawler
import me.kevinp.research.staticanalyzer.maven.MavenCentralDownloader
import me.kevinp.research.staticanalyzer.metrics.MetricsProcessor
import me.kevinp.research.staticanalyzer.model.Artifact
import me.kevinp.research.staticanalyzer.model.ClassMetrics
import me.kevinp.research.staticanalyzer.repository.ArtifactRepository
import me.kevinp.research.staticanalyzer.repository.ClassMetricsRepository
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

    void analyze(){
        crawler.crawl(
            {  json ->
                downloader.download(json.g, json.a, json.v, 'jar',
                        { file ->
                            inspector.unzip(file, {
                                classes ->
                                    def artifact = new Artifact()
                                    artifact.groupId = json.g
                                    artifact.artifactId = json.a
                                    artifact.versionId = json.v

                                    processor.process(classes, addClassMetrics(artifact))

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
