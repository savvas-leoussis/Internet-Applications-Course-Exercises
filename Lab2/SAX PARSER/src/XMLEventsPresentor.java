import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;
import java.util.ArrayList;
import java.io.PrintWriter;

public class XMLEventsPresentor implements ContentHandler
{
    public static class Node {
        public String nodeName;
        public String nodeValue;
        public Attributes attr;
        public Node(String name, Attributes attributes){
            nodeName = name;
            attr= attributes;
        }
    }
    public static ArrayList<ArrayList<Node>> nodeList;
    public static boolean foundTitle = false;
    public static int a=-1;
    private Locator lc;
    public static void main(String[] args) throws Exception
    {
        XMLEventsPresentor xmlep = new XMLEventsPresentor();
        nodeList = new ArrayList<ArrayList<Node>>();
        PrintWriter output = new PrintWriter("output.txt");
        xmlep.go(args[0]);
        for (int i = 0; i < nodeList.size(); i++){
            output.println("\n\t################## LEVEL " + (i+1) + " ##################\n");
            for (int j = 0 ; j < nodeList.get(i).size(); j++){
                Node element = nodeList.get(i).get(j);
                if (element.nodeValue.replaceAll("\n","").replaceAll("\t","").replaceAll(" ","").equals("")){
                    output.println("~~~ Node Name: " + element.nodeName + " - Node Value: null");
                }
                else {
                    output.println("~~~ Node Name: " + element.nodeName + " - Node Value: " + element.nodeValue.replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", ""));
                }
                if (element.attr.getLength() > 0) {
                    output.println("\t\tAttributes:");
                    for (int k = 0; k < element.attr.getLength(); k++){
                        output.println("\t\t\t"+element.attr.getLocalName(k) + " = " + element.attr.getValue(k));
                    }
                }
            }
        }
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