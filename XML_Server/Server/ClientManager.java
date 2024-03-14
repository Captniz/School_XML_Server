package Server;

import java.io.*;
import java.util.*;
import java.net.*;

/**
 * Manages the clients connected to the server.
 */
public class ClientManager extends Thread {
    Socket clientSocket;
    PrintWriter out;
    BufferedReader in;
    XmlManager xml;

    public ClientManager(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //! QUI SI POTREBBE CAMBIARE PER OGNI CLIENT IL FILE DI RESPONSE PER NON FARE CASINI
            xml = new XmlManager("../src/Streamers.xml", "../src/ResponseTemplate.xml", "../src/Response.xml");
        
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        String req;
        String tmp;

        try {
            for (;;) {
                req = in.readLine();

                switch (req) {
                    case "#EXIT":
                        killClient();
                        return;

                    case "#LIST GROUP":
                        // TODO:

                        break;

                    case "#LIST STREAM":
                        // TODO:

                        break;

                    case "#SHOW STREAM":
                        // TODO:

                        break;

                    default:
                        break;
                }

            }
        } catch (Exception e) {
            System.out.println("Errore ClientManager");
            e.printStackTrace();
            killClient();
            return;
        }
    }

    /**
     * Sends an image to the client.
     * 
     * @param path <code>String</code> containing the path of the image to be sent
     */
    void sendImage(String path) {
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            out.println(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Kills the client and removes it from the list of active clients in the
     * streaming service.
     * Calls the <code>clearClients</code> method in <code>ProgettoServerXML</code>.
     */
    void killClient() {
        try {
            clientSocket.close();
            out.close();
            in.close();
            ServerMain.clearClients(this.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    /**
     * Returns the data relative to the request made by the client.
     * 
     * @return <code>String</code> containing the data of the request
     */
    String[] getRequestData(String req) {
        String[] tmp = req.split(" ");
        String[] data = new String[tmp.length - 2];
        for (int i = 2; i < tmp.length; i++) {
            data[i - 2] = tmp[i];
        }

        return data;
    }
}