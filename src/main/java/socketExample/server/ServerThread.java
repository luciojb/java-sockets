package socketExample.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;

/**
 * @author : Lúcio José Beirão
 * @since : 24/08/2022
 **/
public class ServerThread extends Thread {

  private Socket socket;
  private final Hashtable<String, String> dnaMapping = new Hashtable<String, String>() {{
    put("A", "T");
    put("T", "A");
    put("C", "G");
    put("G", "C");
  }};


  public ServerThread(Socket socket) {
    this.socket = socket;
    System.out.println("Novo cliente conectado");
  }

  public void run() {
    try {
      InputStream input = socket.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));

      OutputStream output = socket.getOutputStream();
      PrintWriter writer = new PrintWriter(output, true);

      String text = reader.readLine();

      while (text != null) {
        writer.println(completeDNALineSerial(text));
        text = reader.readLine();
      }

      socket.close();
      System.out.println("Conexão liberada");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private String completeDNALineSerial(String line) {
    StringBuilder newLine = new StringBuilder();
    String[] lineSplit = line.split("");
    for (String c : lineSplit) {
      newLine.append(dnaMapping.get(c));
    }
    return newLine.toString();
  }


}
