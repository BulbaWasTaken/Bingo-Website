package webserver667.responses.authentication;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserPasswordAuthenticator extends UserAuthenticator {

  private PasswordFileReader passwordFileReader;

  public UserPasswordAuthenticator(HttpRequest request, IResource resource) {
    super(request, resource);
  }

  @Override
  public boolean isAuthenticated() {
    try {
      Path passwordFilePath = Paths.get(this.resource.getPath().getParent().toString(), ".passwords");
      this.passwordFileReader = new PasswordFileReader(passwordFilePath);
      String authHeader = this.request.getHeader("Authorization");

      if (authHeader != null && authHeader.startsWith("Basic ")) {
          String base64EncodedString = authHeader.substring("Basic ".length());

          return passwordFileReader.isUserAuthorized(base64EncodedString);
      }
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    
    return false;
  }
}