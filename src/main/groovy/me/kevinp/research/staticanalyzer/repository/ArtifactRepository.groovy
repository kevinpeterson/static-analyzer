package me.kevinp.research.staticanalyzer.repository

import me.kevinp.research.staticanalyzer.model.Artifact
import me.kevinp.research.staticanalyzer.model.ClassMetrics
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class ArtifactRepository {

    @Autowired
    SessionFactory sessionFactory

    def save(bean){
        this.sessionFactory.getCurrentSession().save(bean)
    }

    def read(id){
        this.sessionFactory.getCurrentSession().get(Artifact.class, id)
    }
}
