package de.gesellix.httpclientcachedemo;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

class DemoHandler implements HttpHandler {

  private Logger logger = LoggerFactory.getLogger(getClass());

  public void handle(HttpExchange exchange) throws IOException {
    String response = "foo-bar";

    String method = exchange.getRequestMethod();
    URI uri = exchange.getRequestURI();
    logger.info(method + " " + uri);
//    printHeaders(exchange.getRequestHeaders());

    switch (method) {
      case "HEAD":
        handleHEAD(exchange, response);
        break;
      case "GET":
        handleGET(exchange, response);
        break;
      default:
        exchange.sendResponseHeaders(418, -1);
        break;
    }
  }

  void handleHEAD(HttpExchange exchange, String response) throws IOException {
    Headers headers = exchange.getResponseHeaders();
    headers.add("Server", "Yet Another Example");
    headers.add("ETag", "12345678");
    headers.add("Content-Length", Integer.toString(response.length()));
    headers.add("Cache-Control", "must-revalidate");
    exchange.sendResponseHeaders(200, -1);
  }

  void handleGET(HttpExchange exchange, String response) throws IOException {
    if (isModified(exchange.getRequestHeaders())) {
      logger.info("not cached!");
      Headers headers = exchange.getResponseHeaders();
      headers.add("Server", "Yet Another Example");
      headers.add("ETag", "12345678");
      headers.add("Content-Length", Integer.toString(response.length()));
      headers.add("Cache-Control", "must-revalidate");
      exchange.sendResponseHeaders(200, response.length());
      OutputStream os = exchange.getResponseBody();
      os.write(response.getBytes());
      os.flush();
      os.close();
    }
    else {
      logger.info("cached!");
      Headers headers = exchange.getResponseHeaders();
      headers.add("Server", "Yet Another Example");
      headers.add("ETag", "12345678");
      headers.add("Content-Length", "0");
      headers.add("Cache-Control", "must-revalidate");
      exchange.sendResponseHeaders(304, -1);
      OutputStream os = exchange.getResponseBody();
      os.write(new byte[] {});
      os.flush();
      os.close();
    }
  }

  boolean isModified(Headers headers) {
    if (headers.containsKey("If-None-Match")) {
      if ("12345678".equals(headers.getFirst("If-None-Match"))) {
        return false;
      }
    }
    return true;
  }

  void printHeaders(Headers headers) {
    for (Map.Entry<String, List<String>> listEntry : headers.entrySet()) {
      logger.info("{}: {}", listEntry.getKey(), listAsString(listEntry.getValue()));
    }
  }

  String listAsString(List<String> stringList) {
    String[] a = stringList.toArray(new String[] {});
    return String.join(", ", a);
  }
}
