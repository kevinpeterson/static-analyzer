package me.kevinp.research.staticanalyzer.maven

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import javax.annotation.Resource

import static junit.framework.Assert.assertNotNull

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/static-analyzer.xml")
class MavenCentralDownloaderTest {

    @Resource
    MavenCentralDownloader downloader

    @Test
    void testSetUp(){
        assertNotNull downloader
    }

    @Test
    void testDownload(){
        downloader.download(
                'com.google.code.guice',
                'guice',
                '1.0',
                'jar',
            { file ->
                println file
            }
        )
    }
}
