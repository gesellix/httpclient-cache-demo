# httpclient-cache-demo

Demonstrates a misbehaviour in the [Apache HttpClient Cache library](https://hc.apache.org/httpcomponents-client-ga/httpclient-cache/project-info.html).

````
./gradlew :httpclient-cache-4-3:performDemo

> 17:51:50.736 [main] DEBUG org.apache.http.headers - >> HEAD /db-def/f231e16d0b5958640dfac5059000a0fc HTTP/1.1
> 17:51:50.736 [main] DEBUG org.apache.http.headers - >> Via: 1.1 localhost (Apache-HttpClient/4.3 (cache))
> 17:51:50.736 [main] DEBUG org.apache.http.headers - >> Host: localhost:5984
> 17:51:50.736 [main] DEBUG org.apache.http.headers - >> Connection: Keep-Alive
> 17:51:50.741 [main] DEBUG org.apache.http.headers - << HTTP/1.1 200 OK
> 17:51:50.741 [main] DEBUG org.apache.http.headers - << Server: CouchDB/1.7.1 (Erlang OTP/17)
> 17:51:50.741 [main] DEBUG org.apache.http.headers - << ETag: "1-967a00dff5e02add41819138abb3284d"
> 17:51:50.741 [main] DEBUG org.apache.http.headers - << Date: Sat, 28 Apr 2018 15:51:52 GMT
> 17:51:50.741 [main] DEBUG org.apache.http.headers - << Content-Type: text/plain; charset=utf-8
> 17:51:50.741 [main] DEBUG org.apache.http.headers - << Content-Length: 87
> 17:51:50.741 [main] DEBUG org.apache.http.headers - << Cache-Control: must-revalidate
> 17:51:50.746 [main] DEBUG org.apache.http.headers - >> GET /db-def/f231e16d0b5958640dfac5059000a0fc HTTP/1.1
> 17:51:50.746 [main] DEBUG org.apache.http.headers - >> Via: 1.1 localhost (Apache-HttpClient/4.3 (cache))
> 17:51:50.746 [main] DEBUG org.apache.http.headers - >> Host: localhost:5984
> 17:51:50.746 [main] DEBUG org.apache.http.headers - >> Connection: Keep-Alive
> 17:51:50.749 [main] DEBUG org.apache.http.headers - << HTTP/1.1 200 OK
> 17:51:50.749 [main] DEBUG org.apache.http.headers - << Server: CouchDB/1.7.1 (Erlang OTP/17)
> 17:51:50.749 [main] DEBUG org.apache.http.headers - << ETag: "1-967a00dff5e02add41819138abb3284d"
> 17:51:50.749 [main] DEBUG org.apache.http.headers - << Date: Sat, 28 Apr 2018 15:51:52 GMT
> 17:51:50.749 [main] DEBUG org.apache.http.headers - << Content-Type: text/plain; charset=utf-8
> 17:51:50.749 [main] DEBUG org.apache.http.headers - << Content-Length: 87
> 17:51:50.749 [main] DEBUG org.apache.http.headers - << Cache-Control: must-revalidate
> 17:51:50.753 [main] DEBUG org.apache.http.wire -  << "{"_id":"f231e16d0b5958640dfac5059000a0fc","_rev":"1-967a00dff5e02add41819138abb3284d"}[\n]"
````

````
    ./gradlew :httpclient-cache-4-4:performDemo

> 17:51:58.401 [main] DEBUG org.apache.http.headers - >> HEAD /db-def/f231e16d0b5958640dfac5059000a947 HTTP/1.1
> 17:51:58.401 [main] DEBUG org.apache.http.headers - >> Via: 1.1 localhost (Apache-HttpClient/4.4 (cache))
> 17:51:58.401 [main] DEBUG org.apache.http.headers - >> Host: localhost:5984
> 17:51:58.401 [main] DEBUG org.apache.http.headers - >> Connection: Keep-Alive
> 17:51:58.405 [main] DEBUG org.apache.http.headers - << HTTP/1.1 200 OK
> 17:51:58.405 [main] DEBUG org.apache.http.headers - << Server: CouchDB/1.7.1 (Erlang OTP/17)
> 17:51:58.405 [main] DEBUG org.apache.http.headers - << ETag: "1-967a00dff5e02add41819138abb3284d"
> 17:51:58.405 [main] DEBUG org.apache.http.headers - << Date: Sat, 28 Apr 2018 15:52:00 GMT
> 17:51:58.405 [main] DEBUG org.apache.http.headers - << Content-Type: text/plain; charset=utf-8
> 17:51:58.405 [main] DEBUG org.apache.http.headers - << Content-Length: 87
> 17:51:58.405 [main] DEBUG org.apache.http.headers - << Cache-Control: must-revalidate
> 17:51:58.412 [main] DEBUG org.apache.http.headers - >> GET /db-def/f231e16d0b5958640dfac5059000a947 HTTP/1.1
> 17:51:58.412 [main] DEBUG org.apache.http.headers - >> Via: 1.1 localhost (Apache-HttpClient/4.4 (cache))
> 17:51:58.412 [main] DEBUG org.apache.http.headers - >> If-None-Match: "1-967a00dff5e02add41819138abb3284d"
> 17:51:58.412 [main] DEBUG org.apache.http.headers - >> Cache-Control: max-age=0
> 17:51:58.412 [main] DEBUG org.apache.http.headers - >> Host: localhost:5984
> 17:51:58.412 [main] DEBUG org.apache.http.headers - >> Connection: Keep-Alive
> 17:51:58.417 [main] DEBUG org.apache.http.headers - << HTTP/1.1 304 Not Modified
> 17:51:58.417 [main] DEBUG org.apache.http.headers - << Server: CouchDB/1.7.1 (Erlang OTP/17)
> 17:51:58.417 [main] DEBUG org.apache.http.headers - << ETag: "1-967a00dff5e02add41819138abb3284d"
> 17:51:58.417 [main] DEBUG org.apache.http.headers - << Date: Sat, 28 Apr 2018 15:52:00 GMT
> 17:51:58.417 [main] DEBUG org.apache.http.headers - << Content-Length: 0
> 
> Exception in thread "main" java.lang.NullPointerException
>         at java.io.FilterInputStream.read(FilterInputStream.java:133)
>         at com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper.ensureLoaded(ByteSourceJsonBootstrapper.java:489)
>         at com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper.detectEncoding(ByteSourceJsonBootstrapper.java:126)
>         at com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper.constructParser(ByteSourceJsonBootstrapper.java:215)
>         at com.fasterxml.jackson.core.JsonFactory._createParser(JsonFactory.java:1240)
>         at com.fasterxml.jackson.core.JsonFactory.createParser(JsonFactory.java:802)
>         at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:2796)
>         at org.ektorp.impl.StdCouchDbConnector$5.success(StdCouchDbConnector.java:244)
>         at org.ektorp.http.RestTemplate.handleResponse(RestTemplate.java:126)
>         at org.ektorp.http.RestTemplate.get(RestTemplate.java:22)
>         at org.ektorp.impl.StdCouchDbConnector.get(StdCouchDbConnector.java:240)
>         at org.ektorp.impl.StdCouchDbConnector.get(StdCouchDbConnector.java:231)
>         at org.ektorp.support.CouchDbRepositorySupport.get(CouchDbRepositorySupport.java:148)
>         at de.gesellix.httpclientcachedemo.Demo.doIt(Demo.java:25)
>         at de.gesellix.httpclientcachedemo.Demo.main(Demo.java:12)
````

````
    ./gradlew :httpclient-cache-4-5:performDemo

> 17:52:43.101 [main] DEBUG org.apache.http.headers - >> HEAD /db-def/f231e16d0b5958640dfac5059000b0b1 HTTP/1.1
> 17:52:43.101 [main] DEBUG org.apache.http.headers - >> Via: 1.1 localhost (Apache-HttpClient/4.5 (cache))
> 17:52:43.101 [main] DEBUG org.apache.http.headers - >> Host: localhost:5984
> 17:52:43.101 [main] DEBUG org.apache.http.headers - >> Connection: Keep-Alive
> 17:52:43.104 [main] DEBUG org.apache.http.headers - << HTTP/1.1 200 OK
> 17:52:43.104 [main] DEBUG org.apache.http.headers - << Server: CouchDB/1.7.1 (Erlang OTP/17)
> 17:52:43.104 [main] DEBUG org.apache.http.headers - << ETag: "1-967a00dff5e02add41819138abb3284d"
> 17:52:43.104 [main] DEBUG org.apache.http.headers - << Date: Sat, 28 Apr 2018 15:52:44 GMT
> 17:52:43.104 [main] DEBUG org.apache.http.headers - << Content-Type: text/plain; charset=utf-8
> 17:52:43.105 [main] DEBUG org.apache.http.headers - << Content-Length: 87
> 17:52:43.105 [main] DEBUG org.apache.http.headers - << Cache-Control: must-revalidate
> 17:52:43.115 [main] DEBUG org.apache.http.headers - >> GET /db-def/f231e16d0b5958640dfac5059000b0b1 HTTP/1.1
> 17:52:43.115 [main] DEBUG org.apache.http.headers - >> Via: 1.1 localhost (Apache-HttpClient/4.5 (cache))
> 17:52:43.115 [main] DEBUG org.apache.http.headers - >> If-None-Match: "1-967a00dff5e02add41819138abb3284d"
> 17:52:43.115 [main] DEBUG org.apache.http.headers - >> Cache-Control: max-age=0
> 17:52:43.115 [main] DEBUG org.apache.http.headers - >> Host: localhost:5984
> 17:52:43.115 [main] DEBUG org.apache.http.headers - >> Connection: Keep-Alive
> 17:52:43.121 [main] DEBUG org.apache.http.headers - << HTTP/1.1 304 Not Modified
> 17:52:43.121 [main] DEBUG org.apache.http.headers - << Server: CouchDB/1.7.1 (Erlang OTP/17)
> 17:52:43.121 [main] DEBUG org.apache.http.headers - << ETag: "1-967a00dff5e02add41819138abb3284d"
> 17:52:43.121 [main] DEBUG org.apache.http.headers - << Date: Sat, 28 Apr 2018 15:52:44 GMT
> 17:52:43.121 [main] DEBUG org.apache.http.headers - << Content-Length: 0
> 
> Exception in thread "main" java.lang.NullPointerException
>         at java.io.FilterInputStream.read(FilterInputStream.java:133)
>         at com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper.ensureLoaded(ByteSourceJsonBootstrapper.java:489)
>         at com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper.detectEncoding(ByteSourceJsonBootstrapper.java:126)
>         at com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper.constructParser(ByteSourceJsonBootstrapper.java:215)
>         at com.fasterxml.jackson.core.JsonFactory._createParser(JsonFactory.java:1240)
>         at com.fasterxml.jackson.core.JsonFactory.createParser(JsonFactory.java:802)
>         at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:2796)
>         at org.ektorp.impl.StdCouchDbConnector$5.success(StdCouchDbConnector.java:244)
>         at org.ektorp.http.RestTemplate.handleResponse(RestTemplate.java:126)
>         at org.ektorp.http.RestTemplate.get(RestTemplate.java:22)
>         at org.ektorp.impl.StdCouchDbConnector.get(StdCouchDbConnector.java:240)
>         at org.ektorp.impl.StdCouchDbConnector.get(StdCouchDbConnector.java:231)
>         at org.ektorp.support.CouchDbRepositorySupport.get(CouchDbRepositorySupport.java:148)
>         at de.gesellix.httpclientcachedemo.Demo.doIt(Demo.java:25)
>         at de.gesellix.httpclientcachedemo.Demo.main(Demo.java:12)
````
