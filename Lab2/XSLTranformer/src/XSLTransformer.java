import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;
import java.util.ArrayList;
import java.io.PrintWriter;

public class XSLTransformer implements ContentHandler
{
    public static class Node {
        public String nodeName;
        public String nodeValue;
        public Attributes attr;
        public Node(String name, Attributes attributes){
            nodeName = name;
            attr = attributes;
        }
    }
    public static ArrayList<ArrayList<Node>> nodeList;
    public static boolean foundTitle = false;
    public static int a=-1;
    private Locator lc;
    public static void main(String[] args) throws Exception
    {
        XSLTransformer xmlep = new XSLTransformer();
        nodeList = new ArrayList<ArrayList<Node>>();
        PrintWriter output = new PrintWriter("cars.html");
        xmlep.go(args[0]);
        output.println("<html>");
        output.println("<head>");
        output.println("<META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        output.println("<title>Vehicles</title>");
        output.println("</head>");
        output.println("<body>");
        output.println("<h1 align=\"center\">Vehicles</h1>");
        output.println("<table border=\"2px\" align=\"center\">");
        output.println("<tr><th>Year (a)</th><th>Make (a)</th><th>Model (a)</th><th>Mileage (c)</th><th>Color (c)</th><th>Price (c)</th></tr>");
            for (int i = 0 ; i < nodeList.get(1).size(); i++){
                Node element = nodeList.get(1).get(i);
                if (element.attr.getLength() > 0) {
                    output.println("<tr>");
                    for (int k = 0; k < element.attr.getLength(); k++) {
                        output.println("<td>" + element.attr.getValue(k) + "</td>");
                    }
                }
                for (int j = i*3 ; j < i*3+3 && j < nodeList.get(2).size(); j++){
                    Node child_element = nodeList.get(2).get(j);
                    output.println("<td>" + child_element.nodeValue + "</td>");
                }
                output.println("</tr>");
            }
        output.println("</table>");
        output.println("</body>");
        output.println("</html>");
        output.close();
    }
    public void go(String arg) throws Exception
    {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        XMLReader sr = sp.getXMLReader();
        sr.setContentHandler(this);
        sr.parse(arg);
    }
    public void setDocumentLocator(Locator locator)
    {
        this.lc =locator ;
    }
    public void startDocument() throws SAXException {}
    public void endDocument() throws SAXException {}
    public void startPrefixMapping(String prefix, String uri) throws SAXException {}
    public void endPrefixMapping(String prefix) throws SAXException {}
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException
    {
        a++;
        if(nodeList.size()-1 < a) {
            nodeList.add(new ArrayList<Node>());
        }
        nodeList.get(a).add(new Node(qName, new AttributesImpl(atts)));
        foundTitle = true;
    }
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException
    {
        a--;
    }
    public void characters(char ch[], int start, int lenght) throws SAXException {
        if (foundTitle){
            nodeList.get(a).get(nodeList.get(a).size()-1).nodeValue = new String(ch,start,lenght);
            foundTitle = false;
        }
    }
    public void ignorableWhitespace(char ch[], int start, int lenght) throws SAXException {}
    public void processingInstruction(String target, String data) throws SAXException {}
    public void skippedEntity(String name) throws SAXException {}
}