package de.gesellix.httpclientcachedemo;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.ektorp.support.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Views({@View(
    name = "exampleView",
    map = "function(doc){emit(doc._id,doc._rev)}")}
)
public class CouchDbRepository extends CouchDbRepositorySupport<ExampleDoc> {

  @Autowired
  public CouchDbRepository(@Qualifier("couchdbConnector") CouchDbConnector db) {
    super(ExampleDoc.class, db);
    initStandardDesignDocument();
  }
}
