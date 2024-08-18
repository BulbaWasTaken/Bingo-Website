package webserver667.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;

public class Logger {
  public static String getLogString(
      String ipAddress,
      HttpRequest request,
      IResource resource,
      int statusCode,
      int bytesSent) {
        
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");
    String currentDate = dateFormat.format(new Date());

    String logString = String.format("%s - - [%s] \"%s %s %s\" %d %d",
            ipAddress, currentDate,
            request.getHttpMethod(), request.getUri(), request.getVersion(),
            statusCode, bytesSent);
    return logString;
  }
}