package Main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;

public class IO {
    private final static String FILE = "src/Main/data.xml";

    public static UnrolledLinkedList ReadXML() {
        return ReadXML(6);
    }

    @SuppressWarnings("Duplicates")
    public static UnrolledLinkedList ReadXML(int capacity) {
        UnrolledLinkedList unrolledLinkedList = new UnrolledLinkedList(capacity);

        try {
            File fileXML = new File(FILE);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fileXML);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("pair");

            double length = nList.getLength();

            for (int i = 0; i < length; i++) {
                Node node = nList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String Native = element.getElementsByTagName("native").item(0).getTextContent();
                    String Foreign = element.getElementsByTagName("foreign").item(0).getTextContent();

                    unrolledLinkedList.add(new WordPair(Native, Foreign));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unrolledLinkedList;
    }

    @SuppressWarnings("Duplicates")
    public static LinkedList ReadXML(String a) {
        LinkedList<WordPair> linkedList = new LinkedList<>();

        try {
            File fileXML = new File(FILE);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fileXML);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("pair");

            double length = nList.getLength();

            for (int i = 0; i < length; i++) {
                Node node = nList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String Native = element.getElementsByTagName("native").item(0).getTextContent();
                    String Foreign = element.getElementsByTagName("foreign").item(0).getTextContent();

                    linkedList.add(new WordPair(Native, Foreign));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return linkedList;
    }

    public static void WriteXML(UnrolledLinkedList unrolledLinkedList) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element root = doc.createElement("dictionary");

            for (WordPair wordPair : unrolledLinkedList.toArray()) {
                String sNative = wordPair.Get(0);
                String sForeign = wordPair.Get(1);

                Element eNative = doc.createElement("native");
                eNative.appendChild((doc.createTextNode(sNative)));

                Element eForeign = doc.createElement("foreign");
                eForeign.appendChild((doc.createTextNode(sForeign)));

                Element pair = doc.createElement("pair");
                pair.appendChild(eNative);
                pair.appendChild(eForeign);
                root.appendChild(pair);
            }

            doc.appendChild(root);

            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(FILE)));
        } catch (Exception o) {
            o.printStackTrace();
        }
    }
}
