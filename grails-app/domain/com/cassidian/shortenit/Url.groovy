package com.cassidian.shortenit

import org.hibernate.*

class Url {
    String url
    String shortUri
    Boolean internal
    Long referencedCount

    static String nextUri() {
        Long val = Url.withSession { session ->
            session.createSQLQuery("select nextval('next_uri') as val")
                    .addScalar("next_uri", Hibernate.Long)
                    .uniqueValue()[0]
        }
    }

    static constraints = {
        
    }
}
