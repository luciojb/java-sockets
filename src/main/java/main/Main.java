package main;

import socketExample.client.ClientSocketExample;
import socketExample.server.ServerSocketExample;

/**
 * @author : Lúcio José Beirão
 * @since : 24/08/2022
 **/
public class Main {

  public static void main(String[] args) {
    Thread server = new Thread(new ServerSocketExample());

    Thread c1 = new Thread(new ClientSocketExample("dna-0.txt"));
    Thread c2 = new Thread(new ClientSocketExample("dna-2.txt"));
    Thread c3 = new Thread(new ClientSocketExample("dna-3.txt"));

    server.start();

    c1.start();
    c2.start();
    c3.start();

    try {
      c1.join();
      c2.join();
      c3.join();
      server.interrupt();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }


  }

}
