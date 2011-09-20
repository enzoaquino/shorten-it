package com.cassidian.shortenit

import groovy.sql.Sql

class ShortenItService {
    static def URI_CHARSET = ['0','1','2','3','4','5','6','7','8','9',
                            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z']
	def dataSource

    /**
     * Converts the url to a shortened uri.If it already exists, will returned the existing shortened uri.
     *
     * @param url a non-<code>null</code> url.
     * @return the shortened uri parameter.
     */
    String shortenUrl(String url) {
        if (url == null) return null;
        Url shortenedUrl = Url.findByUrl(url)
        if (!shortenedUrl) {
            shortenedUrl = new Url()
            shortenedUrl.url = url
            shortenedUrl.internal = url.startsWith("/")
            shortenedUrl.shortUri = encodeUri(nextUriValue())
            shortenedUrl.referencedCount = 0
            shortenedUrl.save(failOnError:true)
        }

        return shortenedUrl.shortUri
    }

    /**
     * Fetches the url represented by the shortened uri. If it exists, it incremented the referenced count.
     * 
     * @param uri the shortened uri
     * @return the full url, if it exists, else <code>null</code>
     */
    String fetchFullUrl(String uri) {
        if (uri == null) return null;
        String fullUrl = null
        Url url = Url.findByShortUri(uri)
        if (url) {
            fullUrl = url.url
            // Done as a update to avoid the optimistic locking problem
            Url.executeUpdate(
                "update Url u set u.referencedCount = u.referencedCount + 1 where u.id = :id", 
                [id: url.id])
        }
        return fullUrl
    }

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
