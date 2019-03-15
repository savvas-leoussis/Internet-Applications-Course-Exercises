package BVShop;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.soap.Constants;
import org.apache.soap.Fault;
import org.apache.soap.SOAPException;

import org.apache.soap.encoding.SOAPMappingRegistry;
import org.apache.soap.encoding.soapenc.BeanSerializer;

import org.apache.soap.rpc.Call;
import org.apache.soap.rpc.Parameter;
import org.apache.soap.rpc.Response;

import org.apache.soap.util.xml.QName;

public class BVAdderLister {
    
     public void addlist(URL url, String vmodel,String mname, Integer myear, String mcountry, String vyear )
         throws SOAPException{
         
         // Build the object with the data given by user
         VehicleBean vObj = new VehicleBean(vmodel, new ManBean(mname,myear,mcountry), vyear);
         
         //VehicleBean must now be mapped ... so SOAP can use it
         SOAPMappingRegistry reg = new SOAPMappingRegistry();
         BeanSerializer serializer = new BeanSerializer();
         reg.mapTypes(Constants.NS_URI_SOAP_ENC,
              new QName("urn:VBean_xmlns","vObj"),
              VehicleBean.class, serializer, serializer);
         reg.mapTypes(Constants.NS_URI_SOAP_ENC,
              new QName("urn:MBean_xmlns","mObj"),
              ManBean.class, serializer, serializer);
         //Build the Call object
         Call call = new Call();
         //How to map, where to send, method to call, encoding "style"
         call.setSOAPMappingRegistry(reg);
         call.setTargetObjectURI("urn:BVehicleCatalog");
         call.setMethodName("addV");
         call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);  
         
    
//------------  A D D I N G ---------------------------------------------------
          System.out.println("Adding vehicle model '" + vmodel + "' by " + mname);
            
         // Set up the parameters of the call
         Vector params = new Vector();
          
         //in the instructions given to the 'Serializer' - see Depl. Descr.
         params.addElement(new Parameter("vObj", VehicleBean.class, vObj, null));    
         call.setParams(params);
         
         // Invoke the call
         Response response;
         response = call.invoke(url, "");
         //We do not expect something back, unless there is a fault!!
         if (!response.generatedFault())
              {        System.out.println("Server reported NO FAULT while adding vehicle");}
         else {        Fault fault = response.getFault();
                       System.out.println("Server reported FAULT while adding:");
                       System.out.println(fault.getFaultString());} 
 
 
 //------------  L I S T I N G ---------------------------------------------------
           //We use the same Call Object and change this as appropriate
          /* Another method is now called*/
          call.setMethodName("listV"); 
          /* NO parameters here !!*/
          /*(we cannot have a call with arguments as before)*/              
          call.setParams(null);        
          // Invoke the call; here we expect something back !!
          response = call.invoke(url, ""); 
          
          /*Extract the value returned in the form of a 'Parameter' Object*/
          Parameter returnValue = response.getReturnValue();
          /*Cast the 'Parameter' Object onto a Hashtabel Object*/
          Hashtable catalog = (Hashtable)returnValue.getValue();
          Enumeration e = catalog.keys();
          while (e.hasMoreElements()) {
                String VModel = (String)e.nextElement();
                VehicleBean vo = (VehicleBean)catalog.get(VModel);
                System.out.println("  '" + vo.getVModel() + "' by " + vo.getVManufacturer().getManName() + "(" + vo.getVManufacturer().getManCountry() +" " + vo.getVManufacturer().getManYear() + "), year " + vo.getVYear());
          }
     }                      
 
                       
    
           public static void main(String[] args) {
     if (args.length != 6)
           {System.out.println("Put url, model, manufacturer name,year,county & model year as arguments !!"); 
           return;}
           
        try {
               // URL for SOAP server to connect to
               URL urlink = new URL(args[0]); 
               // Get values for the new vehicle
               String model = args[1];   
               String manufacturer_name = args[2];
               Integer manufacturer_year = Integer.parseInt(args[3]);
               String manufacturer_country = args[4];
               String year = args[5];
               //Add the new vehicle,
               BVAdderLister adderlister = new BVAdderLister();
               adderlister.addlist(urlink, model, manufacturer_name, manufacturer_year, manufacturer_country, year);
        }  catch (Exception e) {e.printStackTrace();}
     }
}  