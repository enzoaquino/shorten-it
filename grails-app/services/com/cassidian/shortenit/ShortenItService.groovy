package com.cassidian.shortenit

import groovy.sql.Sql

class ShortenItService {
    static def URI_CHARSET = ['0','1','2','3','4','5','6','7','8','9',
                            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z']
	def dataSource

	/**
	 * Converts a Long into a base-62 encoded String.
	 * 
	 * @param uriValue a Long greater than 0. 
	 * @return the base-62 (0-9, a-z, A-Z) encoded String. 0 is returned for negative or empty strings.
	 */
	protected String encodeUri(Long uriValue) {
		String encodedValue = ""
		while (uriValue > 0) {
			// cast to an int because the % will return a Long, and there is no charAt(Long)
			encodedValue += URI_CHARSET[(int) (uriValue % URI_CHARSET.size)]
			uriValue = uriValue / URI_CHARSET.size
		}
		// we build the string backwards, so we need to reverse it to get the correct value.
		return encodedValue ? encodedValue.reverse() : "0"
	}
		
	/** 
	 * @return the next Long in the next_uri sequence.
	 */
    protected Long nextUriValue() {
        def sql = new Sql(dataSource)
		return sql.firstRow("SELECT nextval('next_uri') as uri_index").uri_index
    }
}
