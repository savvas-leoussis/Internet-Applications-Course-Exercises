// Setting and Retrieving Cookies
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class form_A extends HttpServlet {
   public void doPost( HttpServletRequest request,                  // reaction to the reception of POST
                       HttpServletResponse response )   throws ServletException, IOException
   {
      PrintWriter output;
      String username = request.getParameter("username");   // choice made will be sent back to client
      String prod_serv = request.getParameter("product_service");
      Cookie c = new Cookie( "username" , username );   // to be stored theree as a cookie
      c.setMaxAge( 1000 );  // seconds until cookie removed
      response.addCookie( c );  // must preceede getWriter
      response.setContentType( "text/html" );
      output = response.getWriter();         
     
       // send HTML page to client
      output.println( "<!DOCTYPE html><html><style>"
+"@import url('https://fonts.googleapis.com/css?family=Ubuntu');"

+"body { background-image: url('images/shattered_@2X.png'); }"

+"h1 { text-shadow: 1px 1px 2px #000000; font-size: 40px; font-family: 'Ubuntu', sans-serif; padding-bottom: 10px; }"

+"h3 { font-size: 19px; font-family: 'Ubuntu', sans-serif; padding-bottom: 10px; }"

+"input[type=text] {"
+"  text-align: center;" 
+"    width: 215px;"
+"    box-sizing: border-box;"
+"    border: 2px solid #ccc;"
+"    border-radius: 4px;"
+"    font-size: 16px;"
+"    padding: 12px 15px 12px 15px;"
+"    -webkit-transition: width 0.4s ease-in-out;"
+"    transition: width 0.4s ease-in-out;"
+"}"

+"input[type=text]:focus {"
 +"   width: 20%;"
+"}"

+"input[type='checkbox'],"
+"input[type='radio'] {"
+"  position: absolute;"
+"  opacity: 0;"
+"  z-index: -1;"
+"}"
+"label {" 
+"  position: relative;"
+"  display: inline-block;"
+"  line-height: 35px;"
+"  width: 80px;"
+"  cursor: pointer;"
+"}"
+"label::before {"
+"  content: ' ';"
+"  position: absolute;"
+"  display: block;"
+"  width: 26px;"
+"  height: 26px;"
+"  z-index: -1;"
+"}"

+"input[type='radio'] + label::before {"
+"  border-radius: 12px;"
+"  left : -5px;"
+"}"
+"input[type='checkbox']:checked + label,"
+"input[type='radio']:checked + label {"
+"  color: #fff;"
+"}"

+"input[type='checkbox']:checked + label::before,"
+"input[type='radio']:checked + label::before {"
+"  padding-left: 10px;"
+"  width: 100%;"
+"  height: 100%;"
+"  background: #000000;"
+"}"
+"label,"
+"label::before {"
+"  -webkit-transition: .25s all ease;"
+"  -o-transition: .25s all ease;"
+"  transition: .25s all ease;"
+"}"

+"input[type=submit] {"
+"    background-color: #000000; /* Green */"
+"    border: none;"
+"    color: white;"
+"    width: 90px;"
+"    padding: 7px 10px;"
+"    font-family: Ubuntu;"
+"    text-align: center;"
+"    text-decoration: none;"
+"    display: inline-block;"
+"    font-size: 16px;"
+"    margin: 4px 2px;"
+"    border-radius: 12px;"
+"    -webkit-transition-duration: 0.4s; /* Safari */"
+"    transition-duration: 0.4s;"
+"    cursor: pointer;"
+"}"
+"input[type=submit] {"
+"    background-color: white;" 
+"    color: black; "
+"    border: 2px solid #000000;"
+"}"

+"input[type=submit]:hover {"
+"    background-color: #000000;"
+"    color: white;"
+"}"

+"select {"
+"  font-family: Ubuntu;"
+"}"

+"div { margin: 20px; }"

+".styled-select {"
+"   background: url(http://i62.tinypic.com/15xvbd5.png) no-repeat 96% 0;"
+"   height: 29px;"
+"   overflow: hidden;"
+"   width: 240px;"
+"}"

+".styled-select select {"
+"   background: transparent;"
+"   border: none;"
+"   font-size: 14px;"
+"   height: 29px;"
+"   padding: 5px;"
+"   width: 268px;"
+"}"

+".semi-square {"
+"   -webkit-border-radius: 5px;"
+"   -moz-border-radius: 5px;"
+"   border-radius: 5px;"
+"}"

+".black    { background-color: #000000; }"

+".black select    { color: #fff; }"

+"select#soflow, select#soflow-color {"
+"   -webkit-appearance: button;"
+"   -webkit-border-radius: 2px;"
+"   -webkit-box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.1);"
+"   -webkit-padding-end: 20px;"
+"   -webkit-padding-start: 2px;"
+"   -webkit-user-select: none;"
+"   background-position: 97% center;"
+"   background-repeat: no-repeat;"
+"   border: 1px solid #AAA;"
+"   color: #555;"
+"   font-size: inherit;"
+"   margin: 20px;"
+"   overflow: hidden;"
+"   padding: 5px 10px;"
+"   text-overflow: ellipsis;"
+"   white-space: nowrap;"
+"   width: 300px;"
+"}"

+"select#soflow-color {"
+"   color: #fff;"
+"   background-color: #779126;"
+"   -webkit-border-radius: 20px;"
+"   -moz-border-radius: 20px;"
+"   border-radius: 20px;"
+"   padding-left: 15px;"
+"}"

+".rbutton {"
+"  display: inline-block;"
+"  border-radius: 8px;"
+"  background-color: #000000;"
+"  border: none;"
+"  color: #FFFFFF;"
+"  font-family: Ubuntu;"
+"    text-align: center;"
+"  font-size: 20px;"
+"  padding: 8px;"
+"  width: 110px;"
+"  transition: all 0.5s;"
+"  cursor: pointer;"
+"  margin: 5px;"
+"}"

+".rbutton span {"
+"  cursor: pointer;"
+"  display: inline-block;"
+"  position: relative;"
+"  transition: 0.5s;"
+"}"

+".rbutton span:after {"
+"  content: '<';"
+"  position: absolute;"
+"  opacity: 0;"
+"  top: 0;"
+"  left: -20px;"
+"  transition: 0.5s;"
+"}"

+".rbutton:hover span {"
+"  padding-left: 25px;"
+"}"

+".rbutton:hover span:after {"
+"  opacity: 1;"
+"  left: 0;"
+"}"
+"</style>"
+"<body>"
+"<center>" );
	  if (prod_serv.equals("products")){
		  output.println("<h1>Products</h1>"
+"<h3>Please choose type of product:</h3>"
+"<form action='form_B' method='POST'>"
+"<div class='styled-select black semi-square'>"	
+"<select name='form_A'>"
+"  <option value='clothing'>Clothing</option>"
+"  <option value='toys'>Toys</option>"
+"  <option value='electronics'>Electronics</option>");
      }
      else if (prod_serv.equals("travel")){
		  output.println("<h1>Travel</h1>"
+"<h3>Please choose type of transport</h3>"
+"<form action='form_B' method='POST'>"
+"	<div class='styled-select black semi-square'>"
+"<select name='form_A'>"
+"  <option value='plane'>Plane</option>"
+"  <option value='train'>Train</option>"
+"  <option value='bus'>Bus</option>");  
      } 
      output.println("</select>"
+"</div>"
+"  <input type='submit' value='Submit'>"
+"</form>" 
+"</center>"
+"</body>"
+"</html>");
      output.close ();    // close stream
   }
}
