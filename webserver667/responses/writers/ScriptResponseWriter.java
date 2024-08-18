package webserver667.responses.writers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

import webserver667.exceptions.responses.ServerErrorException;
import webserver667.requests.HttpRequest;
import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;

public class ScriptResponseWriter extends ResponseWriter {
  public ScriptResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() throws ServerErrorException, IOException {
    try (BufferedReader fileReader = new BufferedReader(new FileReader(this.resource.getPath().toString()))) {
      String interpreter = fileReader.readLine().substring(2).trim();
      ProcessBuilder processBuilder;

      processBuilder = new ProcessBuilder(interpreter,this.resource.getPath().toString());

      Map<String, String> environmentVariables = processBuilder.environment();
      for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
        String headerName = header.getKey();
        String headerValue = header.getValue();
        environmentVariables.put("HTTP_" + headerName, headerValue);
      }
      environmentVariables.put("SERVER_PROTOCOL", request.getVersion());
      if(request.getQueryString() != null)
      {
        environmentVariables.put("QUERY_STRING", request.getQueryString());
      }

      Process process = processBuilder.start();
      try {
        process.waitFor();
      } catch (InterruptedException e) {
        StringBuilder respString = new StringBuilder(
          String.format("HTTP/1.1 %d %s\r\n\r\n",
              HttpResponseCode.INTERNAL_SERVER_ERROR.getCode(),
              HttpResponseCode.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        this.outputStream.write(respString.toString().getBytes());

        this.outputStream.flush();
      }
      byte[] scriptOutput = process.getInputStream().readAllBytes();
      String outputString = new String(scriptOutput, StandardCharsets.UTF_8);
      outputString = outputString.replace("\\r\\n", "\r\n");

      StringBuilder respString = new StringBuilder(
          String.format("HTTP/1.1 %d %s\r\n",
              HttpResponseCode.OK.getCode(),
              HttpResponseCode.OK.getReasonPhrase()));
      respString.append(outputString);
      this.outputStream.write(respString.toString().getBytes());
      this.outputStream.flush();
    }
  }
}
