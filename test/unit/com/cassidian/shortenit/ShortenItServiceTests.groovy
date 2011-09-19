package com.cassidian.shortenit



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ShortenItService)
class ShortenItServiceTests {

    void testEncodeUriZero() {
		assert "0" == service.encodeUri(0);
    }
	
	void testEncodeUri62() {
		assert "10" == service.encodeUri(62)
	}
	
	void testEncodeUri61() {
		assert "Z" == service.encodeUri(61)
	}
	
	void testEncodeUri63() {
		assert "11" == service.encodeUri(63)
	}
	
	void testEncodeUri238328() {
		assert "1000" == service.encodeUri(238328)	
	}
	
	void testEncodeUriNull() {
		assert "0" == service.encodeUri(null)
	}
}
