package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;

import webserver667.exceptions.responses.ServerErrorException;
import webserver667.requests.HttpRequest;
import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;

public class MethodNotAllowedResponseWriter extends ResponseWriter {

  public MethodNotAllowedResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() throws ServerErrorException, IOException{
    StringBuilder respString = new StringBuilder(
              String.format("HTTP/1.1 %d %s\r\n",
                                HttpResponseCode.METHOD_NOT_ALLOWED.getCode(), 
                                HttpResponseCode.METHOD_NOT_ALLOWED.getReasonPhrase()));
    this.outputStream.write(respString.toString().getBytes());
    this.outputStream.write("\r\n".getBytes());
    this.outputStream.flush();
  }
}
