public class ServerTester {
    public static void main(String[] args) {
        try {
            XmlManager xml = new XmlManager("../src/Streamers.xml", "../src/ResponseTemplate.xml", "../src/Response.xml");
            System.out.println("Data file loaded successfully");

            xml.getStreamData("Gaming", "s1");
        } catch (Exception e) {
            System.out.println("Error loading data file");
            e.printStackTrace();
        }
    }
}
