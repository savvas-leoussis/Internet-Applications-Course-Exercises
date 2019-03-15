import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.util.*;
import java.io.PrintWriter;

public class DOMNavigator {
    public static int maxDepth=1;
    public static ArrayList<ArrayList<Node>> Nodes;
    public static void main(String[] args) {
        try {
            PrintWriter output = new PrintWriter("output.txt");
            DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = fact.newDocumentBuilder();
            Document doc = builder.parse(args[0]);
            Nodes = new ArrayList<ArrayList<Node>>();
            Nodes.add(new ArrayList<Node>());
            boolean newLevel;
            orderTree(doc.getDocumentElement(),Nodes.get(0) , 1);
            for (int i = 0; i < Nodes.size(); i++){
                newLevel = true;
                for (int j = 0 ; j < Nodes.get(i).size(); j++){
                    Node element = Nodes.get(i).get(j);
                    if (element.getNodeType() == Node.TEXT_NODE && element.getNodeValue().replaceAll("\n","").replaceAll("\t","").replaceAll(" ","").equals("")) {
                        continue;
                    }
                    if (element.getNodeType() == Node.ELEMENT_NODE && element.getFirstChild().getNodeType() == Node.TEXT_NODE && !element.getFirstChild().getNodeValue().replaceAll("\n","").replaceAll("\t","").replaceAll(" ","").equals("")) {
                        newLevel = printLevel(newLevel,i,output);
                        output.println("~~~ Node Name: " + element.getNodeName() + " - Node Value: " + element.getFirstChild().getNodeValue().replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", ""));
                    }
                    else if(element.getNodeType() == Node.ELEMENT_NODE){
                        newLevel = printLevel(newLevel,i,output);
                        output.println("~~~ Node Name: " + element.getNodeName() + " - Node Value: " + element.getNodeValue());
                    }
                        NamedNodeMap cl = element.getAttributes();
                        if (cl != null && cl.getLength() != 0) {
                            output.println("\t\tAttributes:");
                            for (int k = 0; k < cl.getLength(); k++) {
                                Node node = cl.item(k);
                                output.println("\t\t\t"+node.getNodeName() + " = " + node.getNodeValue());
                            }
                        }
                }
            }
            output.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }
    public static void orderTree(Node doc,ArrayList<Node> nodeList , int depth) {
        if (doc == null) {
            System.out.println("Nothing to print!");
            return;
        }
        nodeList.add(doc);
        NodeList nl = doc.getChildNodes();
        if (nl != null && nl.getLength() != 0) {
            if (maxDepth<depth+1){
                maxDepth = depth+1;
                Nodes.add(new ArrayList<Node>());
            }
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                orderTree(node, Nodes.get(depth),depth + 1);
            }
        }
    }
    public static boolean printLevel(boolean cond,int i,PrintWriter output){
        if (cond){
            output.println("\n\t################## LEVEL " + (i+1) + " ##################\n");
            cond = false;
        }
        return cond;
    }
}