package webserver667;

import java.io.IOException;

import startup.configuration.MimeTypes;
import startup.configuration.ServerConfiguration;

public interface I667Server extends AutoCloseable {
  public void start(ServerConfiguration configuration, MimeTypes mimeTypes) throws IOException;

  public void stop();
}
