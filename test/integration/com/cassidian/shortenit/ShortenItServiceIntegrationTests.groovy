package com.cassidian.shortenit

import static org.junit.Assert.*
import org.junit.*

import groovy.sql.Sql

class ShortenItServiceIntegrationTests {
    def dataSource
    def sql
    def shortenItService
    static transactional = false

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
        assert 1000 == shortenItService.nextUriValue()
    }   
}
