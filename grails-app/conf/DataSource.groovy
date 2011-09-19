dataSource {
    pooled = true
    driverClassName = "org.postgresql.Driver"
    //dialect = org.hibernate.dialect.PostgreSQLDialect
    dialect = net.sf.hibernate.dialect.PostgreSQLDialect
	username = "shorten"
    password = "shorten"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:postgresql://192.168.34.140:5432/shorten"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:postgresql://192.168.34.140:5432/shorten_test"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:prodDb"
            // For MySQL production scenarios enable the following settings
//          pooled = true
//          properties {
//               minEvictableIdleTimeMillis=1800000
//               timeBetweenEvictionRunsMillis=1800000
//               numTestsPerEvictionRun=3
//               testOnBorrow=true
//               testWhileIdle=true
//               testOnReturn=true
//               validationQuery="SELECT 1"
//          }
        }
    }
}
