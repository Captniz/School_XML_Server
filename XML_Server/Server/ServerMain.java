import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Server for a streaming service. It manages the clients and the data they
 * request.
 */
public class ServerMain {
    // #region[#359fa744] //! GLOBAL/STATIC VARS

    static final int PORT = 5566;
    public static ArrayList<ClientManager> clients = new ArrayList<ClientManager>();

    // #endregion

    // #region[#8DA10144] //! MAIN

    public static void main(String[] args) {
        try {
            // Create server socket
            @SuppressWarnings("resource")
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket clientSocket;
            ClientManager clientmanager;
            System.out.println("Server started on " + InetAddress.getLocalHost() + ":" + PORT + " . . . ");

            // Accept and manage clients
            while (true) {
                clientSocket = serverSocket.accept();
                clientmanager = new ClientManager(clientSocket);
                System.out.println("Client connected");

                clients.add(clientmanager);
                clientmanager.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // #endregion

    // #region[#DFA00044] //! METHODS

    /**
     * Removes a client from the list of active clients in the streaming service.
     * 
     * @param name <code>String</code> containing the name of the client to be
     *             removed
     */
    public static void clearClients(String name) {
        for (ClientManager c : clients) {
            if (c.getName().equals(name)) {
                clients.remove(c);
                System.out.println("Client " + name + " removed");
                break;
            }
        }
    }

    // #endregion
}
