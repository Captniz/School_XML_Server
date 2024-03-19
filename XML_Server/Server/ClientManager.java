package Server;

import java.io.*;
import java.net.*;

/**
 * Manages the clients connected to the server.
 */
public class ClientManager extends Thread {
    File xmlFile;
    Socket clientSocket;
    OutputStream out;
    BufferedReader in;
    FileInputStream fis;
    XmlManager xml;
    byte[] buffer;

    public ClientManager(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            out = clientSocket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            xmlFile = new File("../src/Response.xml");
            xml = new XmlManager("../src/Streamers.xml", "../src/ResponseTemplate.xml", "../src/Response.xml");
            fis = new FileInputStream(xmlFile);
            
            buffer = new byte[(int) xmlFile.length()];
        
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        String req;

        try {
            for (;;) {
                req = in.readLine();

                switch (req.split(" ")[0]) {
                    case "#EXIT":
                        killClient();
                        return;

                    case "#LIST_GROUP":
                        xml.getGroupData();
                        sendData();
                        break;

                    case "#LIST_STREAMS":
                        String[] params = getRequestParams(req);
                        xml.getStreams(params[0]);
                        sendData();
                        break;

                    case "#SHOW_STREAM":
                        String[] params2 = getRequestParams(req);
                        xml.getStreamData(params2[0], params2[1]);
                        sendData();
                        break;

                    default:
                        System.out.println("Invalid request");
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
     * Sends the data to the client.
     */
    void sendData() throws IOException{
        xmlFile = new File("../src/Response.xml");
        fis.read(buffer);
        out.write(buffer, 0, buffer.length);
        out.flush();
    }

   
    String[] getRequestParams(String req) {
        return req.substring(req.indexOf(" ") + 1).split(" ");
    }
}


