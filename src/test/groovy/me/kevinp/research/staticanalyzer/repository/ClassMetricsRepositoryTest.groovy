package me.kevinp.research.staticanalyzer.repository

import me.kevinp.research.staticanalyzer.model.ClassMetrics
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.Transactional

import javax.annotation.Resource

import static junit.framework.Assert.*

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/static-analyzer.xml")
class ClassMetricsRepositoryTest {

    @Resource
    ClassMetricsRepository repos

    @Test
    void testSetUp(){
        assertNotNull repos
    }

    @Test
    @Transactional
    void testInsertAndGet(){
        def id = repos.save(new ClassMetrics())
        assertNotNull repos.read(id)
    }
}
