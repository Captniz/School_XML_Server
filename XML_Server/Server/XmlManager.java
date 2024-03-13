package Server;

import java.util.*;
import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

/**
 * Manages the XML file containing the data for the streaming service.
 */
public class XmlManager {
    DocumentBuilder db;
    
    File RESPONSEfile;
    
    File DATAfile;
    Document DATAdoc;
    Node DATAroot;

    public XmlManager(String Dfile, String Rfile) throws Exception {
        db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        
        RESPONSEfile = new File(Rfile);

        DATAfile = new File(Dfile);
        DATAdoc = db.parse(DATAfile);
        DATAroot = DATAdoc.getDocumentElement();
    }

    
    /**
     * Returns all the ELEMENT nodes with the given name in the XML file.
     * 
     * @param nomeNodo <code>String</code> containing the name of the node to be
     *                 searched
     * @return <code>ArrayList<Node></code> containing all the nodes with the given
     *         name
     */
    ArrayList<Node> findNodes(String nomeNodo) {
        NodeList l = DATAdoc.getElementsByTagName(nomeNodo);
        ArrayList<Node> nodes = new ArrayList<Node>();

        for (int i = 0; i < l.getLength(); i++) {
            if (l.item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodes.add(l.item(i));
            }
        }

        return nodes;
    }

    /**
     * Returns the first ELEMENT node with the given name that is a child of the
     * given node.
     * 
     * @param nodo     <code>Node</code> containing the node to be searched
     * @param nomeNodo <code>String</code> containing the name of the node to be
     *                 searched
     * @return <code>Node</code> containing the first node with the given name that
     *         is a child of the given node
     */
    Node findChildNode(Node nodo, String nomeNodo) {
        NodeList l = nodo.getChildNodes();
        Node n = null;

        for (int i = 0; i < l.getLength(); i++) {
            if (l.item(i).getNodeType() == Node.ELEMENT_NODE && l.item(i).getNodeName().equals(nomeNodo)) {
                n = l.item(i);
                break;
            }
        }

        return n;
    }

    /**
     * Returns all the ELEMENT nodes that are children of the given node.
     * 
     * @param nodo <code>Node</code> containing the node to be searched
     * @return <code>ArrayList<Node></code> containing all the children of the given
     *         node
     */
    ArrayList<Node> findChildNodes(Node nodo) {
        NodeList l = nodo.getChildNodes();
        ArrayList<Node> nodes = new ArrayList<Node>();

        for (int i = 0; i < l.getLength(); i++) {
            if (l.item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodes.add(l.item(i));
            }
        }

        return nodes;
    }

    void getGroupData() {
        //TODO
    }

    void getStreams(String groupName) {
        //TODO
    }

    /**

     */
    void getStreamData(String groupName, String streamName) {
        //TODO
    }

    /**

     */
    void saveResponse() throws Exception {
        TransformerFactory.newInstance().newTransformer().transform(
            new javax.xml.transform.dom.DOMSource(DATAdoc),
            new javax.xml.transform.stream.StreamResult(RESPONSEfile));

        DATAdoc = db.parse(DATAfile);
        DATAroot = DATAdoc.getDocumentElement();
    }
}
