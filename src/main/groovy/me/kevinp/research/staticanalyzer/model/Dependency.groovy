package me.kevinp.research.staticanalyzer.model

import javax.persistence.*

@Entity
class Dependency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(optional = false)
    private Artifact artifact;

    String groupId
    String artifactId
    String versionId
}
