package webserver667.responses.writers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import webserver667.exceptions.responses.ServerErrorException;
import webserver667.requests.HttpRequest;
import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;

public class CreatedResponseWriter extends ResponseWriter {

  public CreatedResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() throws ServerErrorException, IOException {
    String body = new String(request.getBody(), StandardCharsets.UTF_8);
    Files.createDirectories(resource.getPath().getParent());
    File contentFile = new File(
      resource.getPath().getParent().toFile(), resource.getPath().getFileName().toString());
    FileWriter writer = new FileWriter(contentFile);
    writer.write(body);
    writer.flush();
    writer.close();
    Files.write(resource.getPath(), resource.getFileBytes(), StandardOpenOption.CREATE);

    StringBuilder respString = new StringBuilder(
              String.format("HTTP/1.1 %d %s\r\n",
                                HttpResponseCode.CREATED.getCode(), 
                                HttpResponseCode.CREATED.getReasonPhrase()));
    this.outputStream.write(respString.toString().getBytes());
    this.outputStream.write("\r\n".getBytes());
    this.outputStream.flush();
  }
}
