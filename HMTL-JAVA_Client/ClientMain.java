import java.io.*;
import java.net.*;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;


/**
 * Client for a streaming service.
 */
public class ClientMain extends WebSocketServer{
    // #region[#359fa744] //! ATTRIBUTES - MAIN - CONSTRUCTOR

    Socket XMLserver;
    PrintWriter out;
    BufferedReader in;

    static final int PORT_HTML = 5567;
    static final int PORT_XML = 5566;
    static final String IP = "127.0.0.1";

    public static void main(String[] args) {
        try {
            
            ClientMain HTMLserver = new ClientMain(new InetSocketAddress(PORT_HTML));
            HTMLserver.start();
            
            System.out.println("Started HTMLserver ...");
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ClientMain(InetSocketAddress address) {
        super(address);
        try {
            XMLserver = new Socket(IP, PORT_XML);
            System.out.println("Connected to XMLserver ...");
            System.out.println("Relay started ...");


            out = new PrintWriter(XMLserver.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(XMLserver.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

    // #endregion

    // #region[#8DA10144] //!EVENTS/METHODS
	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		System.out.println("new connection to " + conn.getRemoteSocketAddress());
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		System.out.println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
        out.println("#EXIT");
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		System.out.println("received message from "	+ conn.getRemoteSocketAddress() + ": " + message);
        out.println(message);

        try {
            int bytesRead;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            InputStream instr = XMLserver.getInputStream();

            while ((bytesRead = instr.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
                System.out.println("Reading from XMLserver ...");
                if (instr.available() < 2) {
                    break;
                }
            }

            System.out.println(baos.toString());
            conn.send(baos.toString());
            baos.close();
        } catch (Exception e) {
            System.out.println("Exception handling message: ");
            e.printStackTrace();
        }
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		System.err.println("an error occurred on connection " + conn.getRemoteSocketAddress()  + ":" + ex);
	}
	
	@Override
	public void onStart() {
		System.out.println("server started successfully");
	}
    // #endregion
}