package com.cassidian.shortenit

import static org.junit.Assert.*
import org.junit.*
import org.hibernate.SessionFactory
import groovy.sql.Sql

class ShortenItServiceIntegrationTests {
    def dataSource
    def sql
    def shortenItService
    SessionFactory sessionFactory

    @Before
    void setUp() {
        sql = new Sql(dataSource)
        sql.execute("ALTER SEQUENCE next_uri RESTART WITH 1000")
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    void testNextUriValue() {
        println "test"
        assert 1000 == shortenItService.nextUriValue()
    }

    void testShortenUrlNull() {
        String shortUri = shortenItService.shortenUrl(null)
        assert null == shortUri
    }

    void testShortenUrlNoExistingInternal() {
        String shortUri = shortenItService.shortenUrl("/test/blah?id=33")
        assert "g8" == shortUri

        Url url = Url.findByShortUri("g8")
        assert null != url
        assert shortUri == url.shortUri, "the shortUri should be the same"
        assert url.internal, "The uri should be considered short." 
        assert url.url == "/test/blah?id=33"
    }

    void testShortenUrlNoExistingExternal() {
        shortenItService.shortenUrl("http://www.google.com")
        
        Url url = Url.findByUrl("http://www.google.com")
        assert null != url
        assert !url.internal
    }

    void testShortenUrlAlreadyExists() {
        String originalUri = shortenItService.shortenUrl("/test")
        String uri = shortenItService.shortenUrl("/test")
        assert originalUri == uri

        flush(); clear(); // because the code does an update, we need to clear out the cache.
        Url url = Url.findByShortUri(uri)
        assert 1 == url.referencedCount
    }

    void flush() {
        sessionFactory.getCurrentSession().flush()
    }

    void clear() {
        sessionFactory.getCurrentSession().clear()
    }
}
