<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo" xmlns:cfdi="http://www.sat.gob.mx/cfd/3" xmlns:TimbreFiscalDigital="http://www.sat.gob.mx/TimbreFiscalDigital"
 xmlns:nomina="http://www.sat.gob.mx/nomina"
xmlns:tfd="http://www.sat.gob.mx/TimbreFiscalDigital" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
<xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>
<xsl:param name="versionParam" select="'1.0'"/> 
<xsl:template match="//cfdi:Comprobante">
<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
      <fo:layout-master-set>
        <fo:simple-page-master master-name="carta" page-height="27.9cm" page-width="21.6cm" margin-top="1cm" margin-bottom="1cm" margin-left="1cm" margin-right="1cm"  >
          <fo:region-body/>
           <fo:region-after/>
        </fo:simple-page-master>
      </fo:layout-master-set>
      <fo:page-sequence master-reference="carta" >
        <fo:flow flow-name="xsl-region-body">
              <fo:block font-size="10pt" absolute-position="absolute"    >
                     <fo:external-graphic   src="url('http://192.168.0.11:8080/factura.jpg')" scaling="uniform"  />
              </fo:block>
       <fo:block-container position="absolute" left="16.0cm" top="0.1cm" width="3.3cm" >
          <fo:block  font-size="8pt" font-weight="bold" text-align="center" color="white" space-after="2mm" ><!--etiqueta !-->
             Recibo No 
          </fo:block>
          <fo:block  font-size="12pt" font-weight="bold" text-align="center">
              <xsl:value-of select="@Serie"/> - <xsl:value-of select="@Folio"/>
          </fo:block>
       </fo:block-container >
       <fo:block-container position="absolute" left="0cm" top="-1cm" width="18cm" color="black"> <!--Cancelado -->
           <fo:block font-size="13pt" color="red" font-weight="bold"  >
	          <xsl:if test="Addenda/@cancelacion">
                        Cancelado :  <xsl:value-of select="Addenda/@cancelacion" />
                  </xsl:if>       
           </fo:block>
       </fo:block-container >
       <fo:block-container position="absolute" left="3cm" top="-0.4cm" width="10cm" color="black"> <!-- ingreso o egreso -->
           <fo:block font-size="11pt" color="black" font-weight="bold" text-align="center" >
	          <xsl:if test="@TipoDeComprobante='ingreso'">
                       Factura
                 </xsl:if> 
	          <xsl:if test="@TipoDeComprobante='egreso'">
   <xsl:choose>
                         
	             <xsl:when test="//nomina:Nomina/@Version ='1.1'">
            Recibo de pago de SUELDO asimilables a salarios 
         </xsl:when>
         <xsl:otherwise>
                       Nota de Crédito
         </xsl:otherwise>
   </xsl:choose>

                 </xsl:if> 

	  </fo:block>
           <fo:block font-size="7pt">
	          <xsl:value-of select="cfdi:Emisor/@Nombre"/>
	  </fo:block>
          <fo:block font-size="7pt">
	          <xsl:apply-templates select="//cfdi:DomicilioFiscal"/>
          </fo:block> 
       </fo:block-container >

        <fo:block-container position="absolute" left="12cm" top="0.1cm"  width="3.2cm" text-align="center" ><!-- fecha !-->
           <fo:block  font-size="8pt" font-weight="bold"  color="white" space-after="2mm"><!--etiqueta !-->
              Fecha
          </fo:block>
             <fo:block  font-size="8pt" space-after="5mm">
            	<xsl:value-of select="@Fecha"/>
              </fo:block> <!--numero de fecha !-->
        </fo:block-container>


      <fo:block-container position="absolute" left="12cm" top="1.5cm" width="3.3cm"  text-align="center" ><!-- cliente !-->
           <fo:block  font-size="8pt" font-weight="bold"  color="white"  space-after="2mm"><!--etiqueta !-->
              Método de pago
          </fo:block>
             <fo:block  font-size="8pt" space-after="5mm">
            	<xsl:value-of select="@MetodoDePago"/>
              </fo:block> 
<!-- RFC del cliente  !-->
        </fo:block-container>   
        

   
        <fo:block-container position="absolute" left="1.5cm" top="2.7cm" width="10cm"> <!--Cliente !-->
	  <fo:block  font-size="9pt" font-weight="bold"  color="white" space-after="1mm" ><!--etiqueta !-->
              Cliente
          </fo:block>
             <fo:block  font-size="8pt" >
            	<xsl:value-of select="cfdi:Receptor/@Nombre"/>
             </fo:block>             
             <fo:block  font-size="8pt" >
            	<xsl:value-of select="cfdi:Receptor/@Rfc"/>
             </fo:block>               
              <xsl:apply-templates select="//cfdi:Domicilio"/>
             
        </fo:block-container>


         

         <fo:block-container position="absolute" left="16cm" top="1.5cm" width="3.3cm" text-align="center" >
          <fo:block  font-size="8pt" font-weight="bold"  color="white"  space-after="2mm" ><!--etiqueta !-->
              Certificado
          </fo:block>
           <fo:block font-size="8pt" >
		<xsl:value-of select="@NoCertificado"/>
	 </fo:block>
         </fo:block-container>
         <fo:block-container position="absolute" left="16cm" top="2.8cm" width="3.3cm"  text-align="center" >
	  <fo:block  font-size="8pt" font-weight="bold"  color="white" space-after="2mm" ><!--etiqueta !-->
             RFC 
          </fo:block>
           <fo:block font-size="6pt" >
			<xsl:value-of select="cfdi:Emisor/@Rfc"/>
            </fo:block>
         </fo:block-container>

	<fo:block-container position="absolute" left="16cm" top="4.1cm" width="3.3cm"  text-align="center" >
	  <fo:block  font-size="8pt" font-weight="bold"  color="white" space-after="2mm" ><!--etiqueta !-->
              Fecha de Timbre
          </fo:block>
           <fo:block font-size="9pt" >
               <xsl:value-of select="//TimbreFiscalDigital:TimbreFiscalDigital/@FechaTimbrado"/>
            </fo:block>
         </fo:block-container>
         <fo:block-container position="absolute" left="12cm" top="2.8cm" width="3.3cm"  text-align="center" >
	  <fo:block  font-size="8pt" font-weight="bold"  color="white" space-after="2mm" ><!--etiqueta !-->
              Certificado del Sat
          </fo:block>
           <fo:block font-size="6pt" >
	    	    <xsl:value-of select="//TimbreFiscalDigital:TimbreFiscalDigital/@NoCertificadoSAT"/>
            </fo:block>
         </fo:block-container>
	<fo:block-container position="absolute" left="12cm" top="4.1cm" width="3.3cm"  text-align="center" >
	  <fo:block  font-size="8pt" font-weight="bold"  color="white" space-after="2mm" ><!--etiqueta !-->
              Lugar Expedición.
          </fo:block>
           <fo:block font-size="5pt" >
               <xsl:value-of select="@LugarExpedicion"/>
            </fo:block>
         </fo:block-container>
 



         <fo:block-container position="absolute" left="0.5cm" top="5.6cm" width="3.3cm">
          <fo:block font-size="8pt">
              <fo:table table-layout="fixed" width="100%" border-collapse="separate">
		
	        <fo:table-column column-width="1.5cm"/>
	        <fo:table-column column-width="2cm"/>
              	<fo:table-column column-width="11.5cm"/>
	        <fo:table-column column-width="2cm"/>
        	<fo:table-column column-width="2cm"/>
		<fo:table-header>
		  <fo:table-cell>
		    <fo:block font-weight="bold">Cantidad</fo:block>
		  </fo:table-cell>
		  <fo:table-cell>
		    <fo:block font-weight="bold">Unidad</fo:block>
		  </fo:table-cell>
		  <fo:table-cell>
		    <fo:block font-weight="bold">Descripción </fo:block>
		  </fo:table-cell>
		  <fo:table-cell>
		    <fo:block font-weight="bold">Precio Unitario </fo:block>
		  </fo:table-cell>
		  <fo:table-cell>
		    <fo:block font-weight="bold">Sub total</fo:block>
		  </fo:table-cell>
		</fo:table-header>
              <fo:table-body>
		 <xsl:apply-templates select="//cfdi:Concepto" />                
              </fo:table-body>
            </fo:table>
          </fo:block>
        </fo:block-container>
<fo:block-container position="absolute" left="0.6cm" top="18.25cm"  width="3cm">
     <fo:block font-size="8pt"  color="white" text-align="center"> 
              Observaciones:
      </fo:block>
      </fo:block-container>
<fo:block-container position="absolute" left="4cm" top="18.1cm"  width="10cm">
     <fo:block font-size="8pt"   
              white-space-collapse="false"
             white-space-treatment="preserve" ><xsl:value-of select="Addenda/@Observaciones"/></fo:block>
      </fo:block-container>
<fo:block-container position="absolute" left="0.6cm" top="19.5cm"  width="15cm">
     <fo:block font-size="6pt" text-align="center"> 
              <xsl:value-of select="@FormaDePago"/>
      </fo:block>
      </fo:block-container>
     <fo:block-container position="absolute" left="0.6cm" top="20cm" >
     <fo:block font-size="6pt"> 
              Importe total con letra:
      </fo:block>
      </fo:block-container>
     <fo:block-container position="absolute" left="2.9cm" top="20cm" >
     <fo:block font-size="8pt"> 
	    <xsl:value-of select="Addenda/@CantidadLetra"/>
      </fo:block>
      </fo:block-container>
      <fo:block-container position="absolute" left="0.2cm" top="14.8cm" width="19cm" >
     
     <fo:block font-size="7pt">Folio Fiscal:  <xsl:value-of select="//TimbreFiscalDigital:TimbreFiscalDigital/@UUID"/>

</fo:block>
     <fo:block font-size="7pt">Cadena Orginal del complemento de certificación digital del SAT:
	   |
<xsl:apply-templates select="//TimbreFiscalDigital:TimbreFiscalDigital"/>
	    
||


      </fo:block>
      <fo:block font-size="7pt"  >
	   Sello digital del CFDI: <xsl:value-of select="//TimbreFiscalDigital:TimbreFiscalDigital/@SelloCFD"/>
      </fo:block>

      </fo:block-container>
      <fo:block-container position="absolute" left="15.0cm" top="18.5cm" width="3.5cm" >
      <fo:block font-size="8pt">
              <fo:table table-layout="fixed" width="100%" border-collapse="separate">
 	        <fo:table-column column-width="2cm"/>
	        <fo:table-column column-width="1cm" text-align="right"/>
                <fo:table-body>
 			<fo:table-row>
			      <fo:table-cell>
			        <fo:block>Subtotal</fo:block>
			      </fo:table-cell>
			      <fo:table-cell>
			        <fo:block><xsl:value-of select='format-number(@SubTotal, "$###,###.00")'/></fo:block>
			      </fo:table-cell>
                       </fo:table-row>
			<fo:table-row>
  			      <fo:table-cell>
			        <fo:block>Retención ISR</fo:block>
			      </fo:table-cell>
			      <fo:table-cell>
			        <fo:block>$<xsl:value-of select='format-number(cfdi:Impuestos/@TotalImpuestosRetenidos,"###,###.00")'/> </fo:block>
			      </fo:table-cell>
		        </fo:table-row>
			<fo:table-row>	
			      <fo:table-cell>
			        <fo:block>Total</fo:block>
			      </fo:table-cell>
			      <fo:table-cell>
			        <fo:block><xsl:value-of select='format-number(@Total, "$###,###.00")'/></fo:block>
			      </fo:table-cell>
		       </fo:table-row>
              	</fo:table-body>
            </fo:table>              
      </fo:block>     
      </fo:block-container>
      <fo:block-container position="absolute" left="0.4cm" top="24.8cm" width="15cm">
      <fo:block font-size="5.5pt"  font-weight="bold" white-space-collapse="true" >
	   Sello del SAT: <xsl:value-of select="//TimbreFiscalDigital:TimbreFiscalDigital/@SelloSAT"/>
      </fo:block>
      </fo:block-container>
<!-- pagare !-->
      <fo:block-container position="absolute" left="0.8cm" top="21.3cm" width="14.3cm">
      <fo:block font-size="6pt">
      </fo:block>
      </fo:block-container>     
     
<fo:block-container position="absolute"  left="1cm" top="23.73cm" font-size="8pt"   white-space-collapse="false" white-space-treatment="preserve" >
      <fo:block text-align="left" >
      </fo:block>
      <fo:block text-align="left" >
      </fo:block>

 </fo:block-container>

      <fo:block-container position="absolute"  left="15.3cm" top="20.6cm" width="5.5cm"  >
      <fo:block >
        <fo:external-graphic src="url('http://localhost:8080/Codificador-war/faces/descargas?serie={@Serie}&amp;folio={@Folio}&amp;rfc={cfdi:Emisor/@Rfc}&amp;tipo=CBB')"  content-height="5cm" content-width="5cm" ></fo:external-graphic>
      </fo:block>
      </fo:block-container>
     

      <fo:block-container position="absolute"   top="25.5cm" >
      <fo:block font-size="7pt"  text-align="center" font-weight="bold">
        Este documento es una representación impresa de un CFDI
      </fo:block>
      </fo:block-container>
<!-- fin no trae la leyendas para cambiarla
	     <fo:block font-size="10pt">
		<xsl:value-of select="@formaDePago"/>	       		
             </fo:block>   !-->
    </fo:flow>
   </fo:page-sequence>
   </fo:root>

  

    
</xsl:template>

<xsl:template match="//cfdi:Concepto">
 <fo:table-row>
      <fo:table-cell>
        <fo:block><xsl:value-of select="@Cantidad"/>
         </fo:block>
      </fo:table-cell>
      <fo:table-cell>
          <fo:block>
          <xsl:value-of select="@Unidad"/>
	  </fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block>
	    <xsl:value-of select="@Descripcion"/>
	 </fo:block>
      </fo:table-cell>      
      <fo:table-cell text-align="right">
        <fo:block text-align="right"><xsl:value-of select='format-number(@ValorUnitario, "$###,###.00")'/>
  </fo:block>
      </fo:table-cell>
      <fo:table-cell text-align="right">
        <fo:block text-align="right">
    <xsl:value-of select='format-number(@Importe, "$###,###.00")'/>
     </fo:block>
      </fo:table-cell>
    </fo:table-row>
 </xsl:template>
<xsl:template match="//cfdi:DomicilioFiscal">	
          <fo:block font-size="7pt">
	    <xsl:value-of select="@Calle"/> #
<xsl:value-of select="@NoExterior"/> - 
<xsl:value-of select="@NoInterior"/>
	  </fo:block>
          <fo:block font-size="7pt">
   <xsl:value-of select="@Colonia"/>,
          <xsl:value-of select="@Municipio"/>,<xsl:value-of select="@Estado"/>
	  </fo:block>
          <fo:block font-size="7pt">
          <xsl:value-of select="@Pais"/>,CP <xsl:value-of select="@CodigoPostal"/>.
	  </fo:block>
           <fo:block >
    Telefonos: (449)9903117
	  </fo:block>
          <fo:block >
            http://www.invenio.mx    servicios@invenio.mx
	  </fo:block>
           <fo:block>
            Regimen Fiscal: Persona Moral en régimen general
	  </fo:block>

       

 </xsl:template>
<xsl:template match="//cfdi:Domicilio">
    <fo:block  font-size="8pt">
	    <xsl:value-of select="@Calle"/> - <xsl:value-of select="@NoExterior"/>  - <xsl:value-of select="@NoInterior"/>
	  </fo:block>
          <fo:block font-size="8pt">
   <xsl:value-of select="@Colonia"/>,
    <xsl:value-of select="@Municipio"/> , <xsl:value-of select="@Estado"/>
    </fo:block>
    <fo:block  font-size="8pt">
    <xsl:value-of select="@Pais"/>, CP <xsl:value-of select="@CodigoPostal"/>.
    </fo:block>
</xsl:template>

<xsl:template match="//cfdi:Traslado">
		       <fo:table-row>
	      <fo:table-cell>
		        <fo:block><xsl:value-of select="@Impuesto"/>&#160;<xsl:value-of select="@Tasa"/>%</fo:block>
             </fo:table-cell>
	     <fo:table-cell>
		        <fo:block><xsl:value-of select='format-number(@Importe, "$###,###.00")'/></fo:block>
	      </fo:table-cell>
		       </fo:table-row>
     
</xsl:template>

<xsl:template name="Requerido">
<xsl:param name="valor"/>
|
<xsl:call-template name="ManejaEspacios">
<xsl:with-param name="s" select="$valor"/>
</xsl:call-template>
</xsl:template>
<xsl:template name="ManejaEspacios">
<xsl:param name="s"/>
<xsl:value-of select="normalize-space(string($s))"/>
</xsl:template>
<xsl:template match="tfd:TimbreFiscalDigital">
<!--
 Iniciamos el tratamiento de los atributos del Timbre
-->
<xsl:call-template name="Requerido">
<xsl:with-param name="valor" select="./@Version"/>
</xsl:call-template>
<xsl:call-template name="Requerido">
<xsl:with-param name="valor" select="./@UUID"/>
</xsl:call-template>
<xsl:call-template name="Requerido">
<xsl:with-param name="valor" select="./@FechaTimbrado"/>
</xsl:call-template>
<xsl:call-template name="Requerido">
<xsl:with-param name="valor" select="./@SelloCFD"/>
</xsl:call-template>
<xsl:call-template name="Requerido">
<xsl:with-param name="valor" select="./@NoCertificadoSAT"/>
</xsl:call-template>
</xsl:template>

</xsl:stylesheet>
