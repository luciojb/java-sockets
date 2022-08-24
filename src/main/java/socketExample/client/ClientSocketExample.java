package socketExample.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Lúcio José Beirão
 * @since : 24/08/2022
 **/
public class ClientSocketExample implements Runnable {


  private String filename;
  private String fullPath;

  public ClientSocketExample(String filename) {
    this.filename = filename;
    this.fullPath = Path.of("src", "main", "resources", "arquivosDNA", filename).toAbsolutePath().toString();
  }

  @Override
  public void run() {
    Socket client = null;
    PrintStream writer = null;
    BufferedReader reader = null;

    try {
      client = new Socket("127.0.0.1", 7000);
      writer = new PrintStream(client.getOutputStream(), true);
      reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
      loadFileData(writer, reader);

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
        if (reader != null) {
          reader.close();
        }
        if (client != null) {
          client.close();
        }

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void loadFileData(PrintStream writer, BufferedReader socketReader) {
    BufferedReader reader;
    List<String> newLines = new ArrayList<>();

    try {
      System.out.printf("Lendo arquivo: %s \n", filename);
      reader = new BufferedReader(new FileReader(fullPath));
      String line = reader.readLine();
      while (line != null) {
        writer.println(line);
        newLines.add(socketReader.readLine());
        // read next line
        line = reader.readLine();
      }
      reader.close();
      writeDNAFile(newLines);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void writeDNAFile(List<String> lines) {
    try {
      FileWriter fw = new FileWriter(fullPath.replace(".txt", "_complemento_socket.txt"));

      for (String line : lines) {
        fw.write(line);
        fw.write(System.lineSeparator());
      }

      fw.close();
    } catch (IOException ie) {
      ie.printStackTrace();
    }
  }
}
