package me.kevinp.research.staticanalyzer

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import javax.annotation.Resource

import static junit.framework.Assert.assertNotNull

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/static-analyzer.xml")
class StaticAnalyzerTest {

    @Resource
    StaticAnalyzer analyzer

    @Test
    void testSetUp(){
        assertNotNull analyzer
    }

    @Test
    void testAnalyze(){
        analyzer.analyze()
    }
}
