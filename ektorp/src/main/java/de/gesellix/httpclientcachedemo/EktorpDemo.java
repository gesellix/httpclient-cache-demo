package de.gesellix.httpclientcachedemo;

import org.ektorp.CouchDbConnector;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;

public class EktorpDemo {

  public static void main(String[] args) throws Exception {
    new EktorpDemo().doIt(args);
  }

  public void doIt(String[] args) throws Exception {
    String tag = args == null || args.length == 0 ? "default" : args[0];

    CouchDbRepository db = getCouchDbRepository("http://localhost:5984", tag);

    ExampleDoc doc = new ExampleDoc();
    db.add(doc);
    if (!db.contains(doc.getId())) {
      throw new IllegalStateException("doc should have been created: " + doc.getId());
    }

    // should update the cache entry
    db.get(doc.getId());
    // should be cached (server might respond with status code 304)
    db.get(doc.getId());
  }

  private CouchDbRepository getCouchDbRepository(String dbUrl, String dbNameSuffix) throws Exception {
    HttpClient httpClient = new StdHttpClient.Builder().url(dbUrl).build();
    StdCouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
    CouchDbConnector dbConnector = new StdCouchDbConnector("db-" + dbNameSuffix, dbInstance);
    dbConnector.createDatabaseIfNotExists();
    return new CouchDbRepository(dbConnector);
  }
}
