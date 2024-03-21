import java.io.*;
import java.net.*;

/**
 * Manages the clients connected to the server.
 */
public class ClientManager extends Thread {
    // #region[#359fa744] //! CONSTRUCTOR AND ATTRIBUTES

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

    // #endregion

    // #region[#8DA10144] //! MAIN/RUN
    @Override
    public void run() {
        String req;

        try {
            for (;;) {
                req = in.readLine();
                System.out.println(req);

                switch (req.split(" ")[0]) {
                    case "#EXIT":
                        System.out.println("Client disconnected");
                        killClient();
                        return;

                    case "#LIST_GROUP":
                        System.out.println("SENDING LIST_GROUP");
                        xml.getGroupData();
                        sendData();
                        break;

                    case "#LIST_STREAMS":
                        System.out.println("SENDING LIST_STREAMS");
                        xml.getStreams(getRequestParams(req));
                        sendData();
                        break;

                    case "#SHOW_STREAM":
                        System.out.println("SENDING SHOW_STREAM");
                        xml.getStreamData(getRequestParams(req));
                        sendData();
                        break;

                    default:
                        System.out.println("Invalid request");
                        System.out.println(req);
                        break;
                }

            }
        } catch (Exception e) {
            System.out.println("Client rimosso da remoto");
            e.printStackTrace();
            killClient();
        }
    }

    // #endregion

    // #region[#DFA00044] //! HELPER METHODS
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
    void sendData() throws IOException {
        out = clientSocket.getOutputStream();
        xmlFile = new File("../src/Response.xml");
        fis = new FileInputStream(xmlFile);

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) > 0) {
            out.write(buffer, 0, bytesRead);
            out.flush();
        }
        System.out.println("Data sent");
    }

    /**
     * Gets the parameters of the request.
     * 
     * @param req <code>String</code> containing the request
     * @return <code>String</code> containing the parameters of the request
     */
    String getRequestParams(String req) {
        return req.substring(req.indexOf(" ") + 1);
    }

    // #endregion
}
