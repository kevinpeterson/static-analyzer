package me.kevinp.research.staticanalyzer.maven

import me.kevinp.research.staticanalyzer.model.Dependency
import org.apache.maven.model.io.xpp3.MavenXpp3Reader
import org.apache.maven.project.MavenProject
import org.springframework.stereotype.Component

/**
 * Created with IntelliJ IDEA.
 * User: m005256
 * Date: 2/10/13
 * Time: 10:54 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
class DependencyChecker {

    def getDependencies(pom){
        def mavenReader = new MavenXpp3Reader();

        def reader = new FileReader(pom);
        def model = mavenReader.read(reader);
        def project = new MavenProject(model);

        def dependencies = []

        project.

        project.getDependencies().each { dependencies << transforMavenDependencies(it) }

        dependencies
    }

    def transforMavenDependencies(org.apache.maven.model.Dependency mavenDependency) {
        def dependency = new Dependency()
        dependency.artifactId = mavenDependency.artifactId
        dependency.groupId = mavenDependency.groupId
        dependency.versionId = mavenDependency.version;

        dependency
    }
}
