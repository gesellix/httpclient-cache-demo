package de.gesellix.httpclientcachedemo;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.impl.ObjectMapperFactory;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.ektorp.spring.HttpClientFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.concurrent.TimeUnit;

@Configuration
public class CouchDbConfig {

  @Autowired
  Environment environment;

  @Autowired
  ObjectMapperFactory objectMapperFactory;

  @Bean
  CouchDbConnector couchdbConnector() throws Exception {
    StdCouchDbConnector db = new StdCouchDbConnector(dbName(), dbInstance(), objectMapperFactory);
    db.createDatabaseIfNotExists();
    return db;
  }

  @Bean
  String dbName() {
    return "db-name";
  }

  @Bean
  CouchDbInstance dbInstance() throws Exception {
    return new StdCouchDbInstance(dbHttpClientFactoryBean().getObject(), objectMapperFactory);
  }

  @Bean
  HttpClientFactoryBean dbHttpClientFactoryBean() {
    HttpClientFactoryBean factoryBean = new HttpClientFactoryBean();
    factoryBean.host = "localhost";
    factoryBean.port = 5984;
    factoryBean.autoUpdateViewOnChange = true;
    // caching doesn't work with Ektorp 1.4.x and HttpClient 4.4+ and recent Spring versions.
    // see https://github.com/helun/Ektorp/issues/222
//    factoryBean.caching = false;
    factoryBean.connectionTimeout = (int) TimeUnit.SECONDS.toMillis(10);
    factoryBean.socketTimeout = (int) TimeUnit.SECONDS.toMillis(20);
    return factoryBean;
  }

  @Bean
  ObjectMapperFactory objectMapperFactory() {
    return new StdObjectMapperFactory();
  }
}
