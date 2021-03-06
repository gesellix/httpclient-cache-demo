package de.gesellix.httpclientcachedemo;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpHead;
import org.apache.hc.client5.http.impl.cache.CacheConfig;
import org.apache.hc.client5.http.impl.cache.CachingHttpClients;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Demo5x {

  private Logger logger = LoggerFactory.getLogger(getClass());

  public static void main(String[] args) throws Exception {
    new Demo5x().doIt(args);
    System.exit(0);
  }

  public void doIt(String[] args) throws Exception {
    String tag = args == null || args.length == 0 ? "default" : args[0];
    String uri = "/example-" + tag;

    DemoServer server = new DemoServer();
    server.start(uri);

    HttpHost httpHost = new HttpHost(server.getAddress(), 8000);

    try {
      CloseableHttpClient client = CachingHttpClients
          .custom()
          .setCacheConfig(CacheConfig.DEFAULT)
          //.createMemoryBound();
          .build();

      String response;

      // should create the cache entry
      logger.info(">> *** 1");
      response = request(client, httpHost, new HttpHead(uri));
      logger.info("<< *** 1: " + response);

      // should update the cache entry
      logger.info(">> *** 2");
      response = request(client, httpHost, new HttpGet(uri));
      if (response != null) {
        logger.info("<< *** 2: " + response);
      }
      else {
        logger.warn("<< *** 2 response body expected, but was empty!");
      }

      // should be cached (server might respond with status code 304)
      logger.info(">> *** 3");
      response = request(client, httpHost, new HttpGet(uri));
      if (response != null) {
        logger.info("<< *** 3: " + response);
      }
      else {
        logger.warn("<< *** 3 response body expected (?), but was empty!");
      }
    }
    finally {
      server.stop();
    }
  }

  private String request(CloseableHttpClient client, HttpHost target, ClassicHttpRequest request) throws IOException {
    CloseableHttpResponse response = null;
    try {
      response = client.execute(target, request);
      if (response.getEntity() != null) {
        return convertStreamToString(response.getEntity().getContent());
      }
      else {
        return null;
      }
    }
    finally {
      if (response != null) {
        response.close();
      }
    }
  }

  static String convertStreamToString(java.io.InputStream is) {
    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
  }
}
