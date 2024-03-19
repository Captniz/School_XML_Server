import java.io.*;
import java.util.*;
import java.net.*;

/**
 * Client for a streaming service.
 */
public class ServerTester {

    // Static & Global variables
    static final int PORT_HTML = 5567;
    static final int PORT_XML = 5566;
    static final String IP = "127.0.0.1";

    public static void main(String[] args) {
        try {
            Socket XMLserver = new Socket(IP, PORT_XML);
            System.out.println("Connected to XMLserver ...");

            ServerSocket HTMLlistener = new ServerSocket(PORT_HTML);
            Socket HTMLserver = HTMLlistener.accept();
            System.out.println("Connected to HTMLserver ...");

            System.out.println("Relay started ...");

            OneWayStream relayHtoX = new OneWayStream(HTMLserver.getInputStream(),XMLserver.getOutputStream());

            OneWayStream relayXtoH = new OneWayStream(XMLserver.getInputStream(),HTMLserver.getOutputStream());

            relayHtoX.start();
            relayXtoH.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class OneWayStream extends Thread {
    InputStream in;
    OutputStream out;

    OneWayStream(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        for (;;) {
            try {
                out.write(in.read());
            } catch (Exception e) {
                System.out.println("Errore Server");
                return;
            }
        }
    }
}
