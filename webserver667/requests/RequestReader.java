package webserver667.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import webserver667.exceptions.responses.BadRequestException;
import webserver667.exceptions.responses.MethodNotAllowedException;

public class RequestReader {

  private InputStream input;
  private HttpRequest request = new HttpRequest();

  public RequestReader(InputStream input) {
    this.input = input;
  }

  public HttpRequest getRequest() throws BadRequestException, MethodNotAllowedException {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));
      String requestLine = reader.readLine();
      
      if (requestLine == null) {
        throw new BadRequestException("Request is NULL.");
      }

      String[] requestBuf = requestLine.split(" ");
      if (requestBuf.length != 3) {
        throw new BadRequestException("Invalid request format.");
      }

      try {
        HttpMethods.valueOf(requestBuf[0]);
      } catch (IllegalArgumentException e) {
        throw new MethodNotAllowedException("Invalid HTTP method.");
      }

      this.request.setHttpMethod(HttpMethods.valueOf(requestBuf[0]));
      this.request.setUri(requestBuf[1]);
      this.request.setVersion(requestBuf[2]);
      
      String headerLine;
      while ((headerLine = reader.readLine()) != null && !headerLine.trim().isEmpty()) {
        this.request.addHeader(headerLine);
      }

      int contentLength = this.request.getContentLength();
      if (contentLength > 0) {
          char[] bodyChars = new char[contentLength];
          int bytesRead = reader.read(bodyChars);
          if (bytesRead != contentLength) {
              throw new BadRequestException("Invalid Content-Length.");
          }
          request.setBody(new String(bodyChars).getBytes());
      }
      return this.request;
    } catch (IOException | IllegalArgumentException e) {
      throw new BadRequestException("Error reading request.");
    }
  }
}