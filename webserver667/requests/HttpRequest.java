package webserver667.requests;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
  private HttpMethods method;
  private String uriStr;
  private String version;
  private Map<String, String> headers = new HashMap<>();
  private byte[] body;
  private String queryString;

  public HttpMethods getHttpMethod() {
    return this.method;
  }

  public void setHttpMethod(HttpMethods method) {
    this.method = method;
  }

  public String getUri() {
    if (uriStr != null) {
      int queryIndex = uriStr.indexOf('?');
      return (queryIndex != -1) ? uriStr.substring(0, queryIndex) : uriStr;
    }
    return null;
  }

  public void setUri(String uri) {
    this.uriStr = uri;
  }

  public String getQueryString() {
    if (uriStr != null) {
      int queryIndex = uriStr.indexOf('?');
      return (queryIndex != -1) ? uriStr.substring(queryIndex + 1, uriStr.length()) : null;
    }
    return null;
  }

  public void setQueryString(String queryString) {
    this.queryString = queryString;
  }

  public String getVersion() {
    return this.version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getHeader(String expectedHeaderName) {
    return headers.get(expectedHeaderName);
  }

  public void addHeader(String headerLine) {
    if (headerLine != null) {
      int colonIndex = headerLine.indexOf(':');
      if (colonIndex != -1) {
          String key = headerLine.substring(0, colonIndex).trim();
          String value = headerLine.substring(colonIndex + 1).trim();
          headers.put(key, value);
      }
    }
  }

  public int getContentLength() {
    for(String key : headers.keySet()) {
      if (key.equalsIgnoreCase("Content-Length")) {
        String contentLengthValue = headers.get(key);
        try {
            return Integer.parseInt(contentLengthValue.trim());
        } catch (NumberFormatException e) {
            System.err.println("Invalid Content-Length value: " + contentLengthValue);
            return -1;
        }
      }
    }
    return 0;
  }

  public byte[] getBody() {
    return (this.body != null) ? this.body : new byte[0];
  }

  public void setBody(byte[] body) {
    this.body = body;
  }

  public boolean hasBody() {
    return (this.body != null) ? true : false;
  }
  public Map<String, String> getHeaders() {
    return headers;
  }
  
}