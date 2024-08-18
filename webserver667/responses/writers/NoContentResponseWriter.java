package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import webserver667.exceptions.responses.ServerErrorException;
import webserver667.requests.HttpRequest;
import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;

public class NoContentResponseWriter extends ResponseWriter {

  public NoContentResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() throws ServerErrorException, IOException {
    Files.delete(resource.getPath());

    StringBuilder respString = new StringBuilder(
              String.format("HTTP/1.1 %d %s\r\n",
                                HttpResponseCode.NO_CONTENT.getCode(), 
                                HttpResponseCode.NO_CONTENT.getReasonPhrase()));
    this.outputStream.write(respString.toString().getBytes());
    this.outputStream.write("\r\n".getBytes());
    this.outputStream.flush();

  }
}
