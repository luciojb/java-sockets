package socketExample.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author : Lúcio José Beirão
 * @since : 24/08/2022
 **/
public class ServerSocketExample implements Runnable {


  @Override
  public void run() {

    try (ServerSocket server = new ServerSocket(7000)) {

      while (true) {
        System.out.println("Aceitando conexões no servidor...");
        Socket conn = server.accept();
        new ServerThread(conn).start();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

  }


}
