package de.gesellix.httpclientcachedemo;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

public class CouchDbRepository extends CouchDbRepositorySupport<ExampleDoc> {

  public CouchDbRepository(CouchDbConnector db) {
    super(ExampleDoc.class, db);
  }
}
