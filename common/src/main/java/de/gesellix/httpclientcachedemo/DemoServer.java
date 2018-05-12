package de.gesellix.httpclientcachedemo;

import com.sun.net.httpserver.HttpServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class DemoServer {

  private HttpServer server = null;

  public InetAddress getAddress() {
    return server.getAddress().getAddress();
  }

  public void start(String uri) throws Exception {
    server = HttpServer.create(new InetSocketAddress("127.0.0.1", 8000), 8000);
    server.createContext(uri, new DemoHandler());
    server.setExecutor(null); // creates a default executor
    server.start();
  }

  public void stop() {
    server.stop(0);
  }
}
