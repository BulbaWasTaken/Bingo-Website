package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import webserver667.requests.HttpMethods;
import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;

public class ResponseWriterFactory {
  public static ResponseWriter create(OutputStream out, IResource resource, HttpRequest request) {
    if(resource.isProtected()){
      if(request.getHeader("Authorization") == null){
        return new UnauthorizedResponseWriter(out, resource, request);
      } 
      return (resource.getUserAuthenticator().isAuthenticated()) ? createHelper(out, resource, request) : new ForbiddenResponseWriter(out, resource, request);
    }
    return createHelper(out, resource, request);
  }

  private static ResponseWriter createHelper(OutputStream out, IResource resource, HttpRequest request){
    SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss z");
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

    HttpMethods httpMethod = request.getHttpMethod();
    if (!resource.exists() && httpMethod != HttpMethods.PUT){
      return new NotFoundResponseWriter(out, resource, request);
    }
    switch (httpMethod) {
      case GET:
        if (!resource.exists()) {
          return new NotFoundResponseWriter(out, resource, request);
        } 
        String ifModifiedSinceHeader = request.getHeader("If-Modified-Since");
        if (ifModifiedSinceHeader != null) {
          try {
            Date ifModifiedSinceDate = dateFormat.parse(ifModifiedSinceHeader);
            Date fileLastModifiedDate = new Date(resource.lastModified());

            if (!fileLastModifiedDate.after(ifModifiedSinceDate)) {
              request.setBody(resource.getFileBytes());
              return new NotModifiedResponseWriter(out, resource, request);
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        } else {
          try {
            request.setBody(resource.getFileBytes());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        return new OkResponseWriter(out, resource, request);
      case HEAD:
      if (!resource.exists()) {
          return new NotFoundResponseWriter(out, resource, request);
      }
      return new OkResponseWriter(out, resource, request);
      case POST:
        if (!resource.exists()) {
          return new NotFoundResponseWriter(out, resource, request);
        }
        return new ScriptResponseWriter(out, resource, request);
      case PUT:
        return new CreatedResponseWriter(out, resource, request);
      case DELETE:
        if (!resource.exists()) {
          return new NotFoundResponseWriter(out, resource, request);
        }
        return new NoContentResponseWriter(out, resource, request);
      default:
        return null;
    }
  }
}


