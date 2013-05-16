<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
  <head>
  <style>
  
  thead tr {
  background-color: #ffff66
  }

  tbody tr.even {
  background-color: #ccffcc
  }
  
  tbody tr.odd {
  background-color: #ffcccc
  }
  
  th, td {
  padding-top: 0.5em;
  padding-bottom: 0.5em;
  padding-left: 0.5em;
  padding-right: 0.5em;
  }
   
  </style>
</head>

<body>


  <xsl:for-each select="root/releasenotes">
  <xsl:copy-of select="introduction"/>
  </xsl:for-each>
 
<h2>De lijst:</h2>

<table>
<thead>
    <tr>
      <th align="left">Rang</th>
      <th align="left">Voorvoegsel</th>      
      <th align="left">Naam</th>
      <th align="left">Naamdragers 2007</th>
      <th align="left">Naamdragers 1947</th>
    </tr>
</thead>
<tbody>
    <xsl:for-each select="root/record">

 <xsl:variable name="colour">   
    <xsl:choose>
        <xsl:when test="position() mod 2 ">odd</xsl:when>
        <xsl:otherwise>even</xsl:otherwise>
    </xsl:choose>
 </xsl:variable>   


   <tr class="{$colour}">
      <td><xsl:value-of select="rang"/></td>
      <td><xsl:value-of select="prefix"/></td>
      <td><xsl:value-of select="naam"/></td>
      <td><xsl:value-of select="n2007"/></td>
      <td><xsl:value-of select="n1947"/></td>
    </tr>
    
    </xsl:for-each>
</tbody>
</table>

</body>
</html>
</xsl:template>

</xsl:stylesheet>