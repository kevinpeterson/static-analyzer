package me.kevinp.research.staticanalyzer.model

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

import static javax.persistence.FetchType.*

@Entity
class Artifact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String groupId
    String artifactId
    String versionId

    @OneToMany(cascade = CascadeType.ALL, mappedBy="artifact")
    List<ClassMetrics> classMetrics = []

    @OneToMany(cascade = CascadeType.ALL, mappedBy="artifact")
    List<Dependency> dependencies = []

    def addClassMetrics(metrics){
        metrics.artifact = this
        classMetrics.add(metrics)
    }

    def addDependency(dependency){
        dependency.artifact = this
        dependencies.add(dependency)
    }
}
