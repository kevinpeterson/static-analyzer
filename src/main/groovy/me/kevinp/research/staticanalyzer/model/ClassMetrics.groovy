package me.kevinp.research.staticanalyzer.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class ClassMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(optional = false)
    private Artifact artifact;

    String className
    int dit
    int noc
}
