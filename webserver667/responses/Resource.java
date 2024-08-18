package webserver667.responses;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import startup.configuration.MimeTypes;
import webserver667.requests.HttpMethods;
import webserver667.requests.HttpRequest;
import webserver667.responses.authentication.UserAuthenticator;
import webserver667.responses.authentication.UserPasswordAuthenticator;

public class Resource implements IResource {

  private final Path originPath;
  private HttpRequest request;
  private MimeTypes mimeTypes;

  public Resource(HttpRequest request, String documentRoot, MimeTypes mimeTypes) {
    this.mimeTypes = mimeTypes;
    this.request = request;
    this.originPath = Paths.get(documentRoot, request.getUri());
  }

  @Override
  public boolean exists() {
    return Files.exists(originPath);
  }

  @Override
  public Path getPath() {
    return this.originPath;
  }

  @Override
  public boolean isProtected() {
    Path directoryPath = originPath.getParent();
    File passwordsFile = new File(directoryPath.toFile(), ".passwords");
    return passwordsFile.exists();
  }

  @Override
  public boolean isScript() {
   return this.request.getHttpMethod() == HttpMethods.POST;
  }

  @Override
  public UserAuthenticator getUserAuthenticator() {
    return new UserPasswordAuthenticator(this.request, this);
  }

  @Override
  public String getMimeType() {
    StringBuilder extension = new StringBuilder(); 
    int lastDotIndex = this.request.getUri().lastIndexOf(".");
    if (lastDotIndex != -1) {
        extension.append(this.request.getUri().substring(lastDotIndex + 1));
    }
    return this.mimeTypes.getMimeTypeByExtension(extension.toString());
  }

  @Override
  public long getFileSize() throws IOException {
    return Files.size(originPath);
  }

  @Override
  public byte[] getFileBytes() throws IOException {
    return Files.readAllBytes(originPath);
  }

  @Override
  public long lastModified() {
    try {
      return Files.getLastModifiedTime(originPath).toMillis();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }

}