import java.util.*;
import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;

import org.w3c.dom.*;

/**
 * Manages the XML file containing the data for the streaming service.
 */
public class XmlManager {
    // #region[#359fa744] //! CONSTRUCTOR AND ATTRIBUTES

    private DocumentBuilder db;

    String ResponseLocation;

    private File ResponseTemplatefile;
    private Document RESPONSEdoc;
    private Node RESPONSEroot;

    private File DATAfile;
    private Document DATAdoc;
    private Node DATAroot;

    /**
     * Constructor for the XmlManager class.
     * 
     * @param Dfile     <code>String</code> containing the path to the Data XML file
     * @param RTfile    <code>String</code> containing the path to the Response
     *                  Template XML file
     * @param Rlocation <code>String</code> containing the path to the Response XML
     *                  file
     */
    public XmlManager(String Dfile, String RTfile, String Rlocation) throws Exception {
        db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        ResponseTemplatefile = new File(RTfile);
        RESPONSEdoc = db.parse(ResponseTemplatefile);
        RESPONSEroot = RESPONSEdoc.getDocumentElement();

        DATAfile = new File(Dfile);
        DATAdoc = db.parse(DATAfile);
        DATAroot = DATAdoc.getDocumentElement();

        ResponseLocation = Rlocation;
    }
    // #endregion

    // #region[#8DA10144] //! HELPER METHODS

    /**
     * Returns all the ELEMENT nodes with the given name in the XML file.
     * 
     * @param nomeNodo <code>String</code> containing the name of the node to be
     *                 searched
     * @return <code>ArrayList<Node></code> containing all the nodes with the given
     *         name
     */
    private ArrayList<Node> findNodes(Document doc, String nomeNodo) {
        NodeList l = doc.getElementsByTagName(nomeNodo);
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
    private Node findChildNode(Node nodo, String nomeNodo) {
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
    private ArrayList<Node> findChildNodes(Node nodo) {
        NodeList l = nodo.getChildNodes();
        ArrayList<Node> nodes = new ArrayList<Node>();

        for (int i = 0; i < l.getLength(); i++) {
            if (l.item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodes.add(l.item(i));
            }
        }

        return nodes;
    }

    
    private Node findNodeWithAttr(Node nodo, String attrName, String attrValue) {
        if (nodo == null) {
            return null;
        }

        if (nodo.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) nodo;
            if (element.getAttribute(attrName).equals(attrValue)) {
                return element;
            }
        }

        NodeList childNodes = nodo.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            Node foundNode = findNodeWithAttr(childNode, attrName, attrValue);
            if (foundNode != null) {
                return foundNode;
            }
        }

        return null;
    }

    /**
     * Returns the value of the first TEXT_NODE that is a child of the given node.
     * 
     * @param nodo <code>Node</code> containing the node to be searched
     * @return <code>String</code> containing the value of the first text node that
     *         is a child of the given node
     */
    private void saveResponse() throws Exception {
        TransformerFactory.newInstance().newTransformer().transform(
                new javax.xml.transform.dom.DOMSource(RESPONSEdoc),
                new javax.xml.transform.stream.StreamResult(ResponseLocation));

        RESPONSEdoc = db.parse(ResponseTemplatefile);
        RESPONSEroot = RESPONSEdoc.getDocumentElement();
    }

    // #endregion

    // #region[#DFA00044] //! PUBLIC METHODS

    /**
     * Returns in the file for the RESPONSE all the groups from the DATA file,
     * whitout superficial info.
     */
    public void getGroupData() {
        ArrayList<Node> gruppi = findNodes(DATAdoc, "group");
        for (Node gruppo : gruppi) {
            Element importedGruppo = (Element) RESPONSEdoc.importNode(gruppo.cloneNode(true), true);
            RESPONSEroot.appendChild(importedGruppo);
        }

        gruppi = findNodes(RESPONSEdoc, "group");

        for (Node gruppo : gruppi) {
            gruppo.removeChild(findChildNode(gruppo, "streamers"));
        }

        try {
            saveResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns in the file for the RESPONSE all the streams from the DATA file for
     * the given group, whitout superficial info.
     * 
     * @param groupName <code>String</code> containing the name of the group to be
     *                  searched
     */
    public void getStreams(String groupName) {
        Node streams = findChildNode(findNodeWithAttr(DATAroot, "genre", groupName), "streamers");

        Element importedStreamers = (Element) RESPONSEdoc.importNode(streams.cloneNode(true), true);
        RESPONSEroot.appendChild(importedStreamers);

        ArrayList<Node> streamers = findNodes(RESPONSEdoc, "streamer");

        try {
            saveResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns in the file for the RESPONSE all the data from the DATA file for the
     * given group and stream.
     * 
     * @param streamName <code>String</code> containing the name of the stream to be
     *                   searched
     */
    public void getStreamData(String streamName) {
        Node stream = findNodeWithAttr(DATAroot, "name", streamName);

        Element importedStream = (Element) RESPONSEdoc.importNode(stream.cloneNode(true), true);
        RESPONSEroot.appendChild(importedStream);

        try {
            saveResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // #endregion
}
