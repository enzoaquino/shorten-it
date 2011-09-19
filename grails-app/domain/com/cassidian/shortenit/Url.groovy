package com.cassidian.shortenit

import org.hibernate.*

class Url {
    String url
    String shortUri
    Boolean internal
    Long referencedCount

    static constraints = {
        
    }
}
