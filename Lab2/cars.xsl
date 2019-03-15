<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:template match="/">
     <html>
       <head>
         <title>Vehicles</title>
       </head>

       <body>
         <h1 align="center">Vehicles</h1>
         <table align="center" border="2px">
           <tr>
             <th>Year (a)</th>
             <th>Make (a)</th>
             <th>Model (a)</th>
             <th>Mileage (c)</th>
             <th>Color (c)</th>
             <th>Price (c)</th>
           </tr>
            <xsl:for-each select="vehicles/vehicle">
				<tr>
					<td><xsl:value-of select="@year"/></td>
					<td><xsl:value-of select="@make"/></td>
					<td><xsl:value-of select="@model"/></td>
					<td><xsl:value-of select="mileage"/></td>
					<td><xsl:value-of select="color"/></td>
					<td><xsl:value-of select="price"/></td>
				</tr>
			</xsl:for-each>
         </table>
       </body>
     </html>
   </xsl:template>
 </xsl:stylesheet>