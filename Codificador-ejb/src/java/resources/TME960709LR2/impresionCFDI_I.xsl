<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet exclude-result-prefixes="fo" version="1.0" xmlns:TimbreFiscalDigital="http://www.sat.gob.mx/TimbreFiscalDigital" xmlns:cfdi="http://www.sat.gob.mx/cfd/3" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:nomina="http://www.sat.gob.mx/nomina" xmlns:tfd="http://www.sat.gob.mx/TimbreFiscalDigital" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output indent="yes" method="xml" omit-xml-declaration="no" version="1.0"/>
  <xsl:param name="versionParam" select="'1.0'"/>
  <xsl:template match="//cfdi:Comprobante">
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
      <fo:layout-master-set>
        <fo:simple-page-master margin-bottom="1cm" margin-left="1cm" margin-right="1cm" margin-top="1cm" master-name="carta" page-height="27.9cm" page-width="21.6cm">
          <fo:region-body/>
          <fo:region-after/>
        </fo:simple-page-master>
      </fo:layout-master-set>
      <fo:page-sequence master-reference="carta">
        <fo:flow flow-name="xsl-region-body">
          <fo:block absolute-position="absolute" font-size="10pt">
            <fo:external-graphic scaling="uniform" src="url('http://192.168.0.11:8080/factura.jpg')"/>
          </fo:block>
          <fo:block-container left="16.0cm" position="absolute" top="0.1cm" width="3.3cm">
            <fo:block color="white" font-size="8pt" font-weight="bold" space-after="2mm" text-align="center">
              <!--etiqueta !-->Serie/Folio </fo:block>
            <fo:block font-size="12pt" font-weight="bold" text-align="center">
              <xsl:value-of select="@Serie"/>- 
              <xsl:value-of select="@Folio"/></fo:block>
          </fo:block-container>
          <fo:block-container color="black" left="0cm" position="absolute" top="-1cm" width="18cm">
            <!--Cancelado -->
            <fo:block color="red" font-size="13pt" font-weight="bold">
              <xsl:if test="Addenda/@cancelacion">Cancelado :  
                <xsl:value-of select="Addenda/@cancelacion"/></xsl:if>
            </fo:block>
          </fo:block-container>
          <fo:block-container color="black" left="3cm" position="absolute" top="0.5cm" width="10cm">
            <!-- ingreso o egreso -->
            <fo:block color="black" font-size="11pt" font-weight="bold" text-align="center">
              <xsl:if test="@TipoDeComprobante='I Ingreso'">Factura
                 </xsl:if>
              <xsl:if test="@TipoDeComprobante='E Egreso'">
                <xsl:choose>
                  <xsl:when test="//nomina:Nomina/@Version ='1.1'">Recibo de pago de SUELDO asimilables a salarios 
         </xsl:when>
                  <xsl:otherwise>Nota de Crédito
         </xsl:otherwise>
                </xsl:choose>
              </xsl:if>
            </fo:block>
            <fo:block font-size="12pt">
              <xsl:value-of select="cfdi:Emisor/@Nombre"/>
            </fo:block>

            <fo:block font-size="7pt">
              <xsl:apply-templates select="//cfdi:DomicilioFiscal"/>
            </fo:block>
          </fo:block-container>
          <fo:block-container left="12cm" position="absolute" text-align="center" top="0.1cm" width="3.2cm">
            <!-- fecha !-->
            <fo:block color="white" font-size="8pt" font-weight="bold" space-after="2mm">
              <!--etiqueta !-->Fecha</fo:block>
            <fo:block font-size="8pt" space-after="5mm">
              <xsl:value-of select="@Fecha"/>
            </fo:block>
            <!--numero de fecha !-->
          </fo:block-container>
          <fo:block-container left="12cm" position="absolute" text-align="center" top="1.4cm" width="3.3cm">
            <!-- cliente !-->
            <fo:block color="white" font-size="8pt" font-weight="bold" space-after="2mm">
              <!--etiqueta !-->
 RfcProvCertif:  
            </fo:block>
            <fo:block font-size="8pt" space-after="5mm">
              <xsl:value-of select="//TimbreFiscalDigital:TimbreFiscalDigital/@RfcProvCertif"/>
            </fo:block>
            <!-- RFC del cliente  !-->
          </fo:block-container>
          <fo:block-container left="1.5cm" position="absolute" top="2.7cm" width="10cm">
            <!--Cliente !-->
            <fo:block color="white" font-size="9pt" font-weight="bold" space-after="1mm">
              <!--etiqueta !-->Cliente</fo:block>
            <fo:block font-size="8pt">
              <xsl:value-of select="cfdi:Receptor/@Nombre"/>
            </fo:block>
            <fo:block font-size="8pt">
              <xsl:value-of select="cfdi:Receptor/@Rfc"/>
            </fo:block>
            <fo:block font-size="8pt">Uso CFDI :
              <xsl:value-of select="cfdi:Receptor/@UsoCFDI"/>
            </fo:block>
            <fo:block font-size="8pt">Forma Pago :
              <xsl:value-of select="@FormaPago"/>
            </fo:block>
            <fo:block font-size="8pt">Método de pago :
              <xsl:value-of select="@MetodoPago"/>
            </fo:block>
            <xsl:apply-templates select="//cfdi:Domicilio"/>
          </fo:block-container>
          <fo:block-container left="16cm" position="absolute" text-align="center" top="1.5cm" width="3.3cm">
            <fo:block color="white" font-size="8pt" font-weight="bold" space-after="2mm">
              <!--etiqueta !-->Certificado</fo:block>
            <fo:block font-size="8pt">
              <xsl:value-of select="@NoCertificado"/>
            </fo:block>
          </fo:block-container>
          <fo:block-container left="16cm" position="absolute" text-align="center" top="2.8cm" width="3.3cm">
            <fo:block color="white" font-size="8pt" font-weight="bold" space-after="2mm">
              <!--etiqueta !-->RFC </fo:block>
            <fo:block font-size="8pt">
              <xsl:value-of select="cfdi:Emisor/@Rfc"/>
            </fo:block>
          </fo:block-container>
          <fo:block-container left="16cm" position="absolute" text-align="center" top="4.1cm" width="3.3cm">
            <fo:block color="white" font-size="8pt" font-weight="bold" space-after="2mm">
              <!--etiqueta !-->Fecha de Timbre</fo:block>
            <fo:block font-size="9pt">
              <xsl:value-of select="//TimbreFiscalDigital:TimbreFiscalDigital/@FechaTimbrado"/>
            </fo:block>
          </fo:block-container>
          <fo:block-container left="12cm" position="absolute" text-align="center" top="2.8cm" width="3.3cm">
            <fo:block color="white" font-size="8pt" font-weight="bold" space-after="2mm">
              <!--etiqueta !-->Certificado del Sat</fo:block>
            <fo:block font-size="8pt">
              <xsl:value-of select="//TimbreFiscalDigital:TimbreFiscalDigital/@NoCertificadoSAT"/>
            </fo:block>
          </fo:block-container>
          <fo:block-container left="12cm" position="absolute" text-align="center" top="4.1cm" width="3.3cm">
            <fo:block color="white" font-size="8pt" font-weight="bold" space-after="2mm">
              <!--etiqueta !-->Lugar Expedición.</fo:block>
            <fo:block font-size="8pt">
              <xsl:value-of select="@LugarExpedicion"/>
            </fo:block>
          </fo:block-container>
          <fo:block-container left="0.5cm" position="absolute" top="5.6cm" width="3.3cm">
            <fo:block font-size="7.5pt">
              <fo:table border-collapse="separate" table-layout="fixed" width="100%">
                <fo:table-column column-width="2cm"/>
                <fo:table-column column-width="1.5cm"/>
                <fo:table-column column-width="1.5cm"/>
                <fo:table-column column-width="8cm"/>
                <fo:table-column column-width="2cm"/>
                <fo:table-column column-width="2cm"/>
                <fo:table-column column-width="2cm"/>
                <fo:table-header>
                  <fo:table-cell>
                    <fo:block font-weight="bold">ClaveProdServ</fo:block>
                  </fo:table-cell>
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
                  <fo:table-cell>
                    <fo:block font-weight="bold">Descuento</fo:block>
                  </fo:table-cell>
                </fo:table-header>
                <fo:table-body>
                  <xsl:apply-templates select="//cfdi:Concepto"/>
                </fo:table-body>
              </fo:table>
            </fo:block>
          </fo:block-container>
          <fo:block-container left="0.6cm" position="absolute" top="18.25cm" width="3cm">
            <fo:block color="white" font-size="8pt" text-align="center">Observaciones:
      </fo:block>
          </fo:block-container>
          <fo:block-container left="4cm" position="absolute" top="18.1cm" width="10cm">
            <fo:block font-size="8pt" white-space-collapse="false" white-space-treatment="preserve">
              <xsl:value-of select="Addenda/@Observaciones"/>
            </fo:block>
          </fo:block-container>
          <fo:block-container left="0.6cm" position="absolute" top="19.5cm" width="15cm">
            <fo:block font-size="6pt" text-align="center">
              <xsl:value-of select="@FormaDePago"/>
            </fo:block>
          </fo:block-container>
          <fo:block-container left="0.6cm" position="absolute" top="20cm">
            <fo:block font-size="6pt">Importe total con letra:
      </fo:block>
          </fo:block-container>
          <fo:block-container left="2.9cm" position="absolute" top="20cm">
            <fo:block font-size="8pt">
              <xsl:value-of select="//cfdi:Addenda/clienteDatos/@totalenletra"/>
            </fo:block>
          </fo:block-container>
          <fo:block-container left="0.2cm" position="absolute" top="14.8cm" width="18cm">
            <fo:block font-size="6pt" font-weight="bold" white-space-collapse="true" >Tipo de Comprobante :  
              <xsl:value-of select="@TipoDeComprobante"/>
               </fo:block>
            <fo:block font-size="6pt" font-weight="bold" white-space-collapse="true" >Fecha Timbrado:  
              <xsl:value-of select="//TimbreFiscalDigital:TimbreFiscalDigital/@FechaTimbrado"/>
               </fo:block>
            <fo:block font-size="6pt" font-weight="bold" white-space-collapse="true" >UUID:  
              <xsl:value-of select="//TimbreFiscalDigital:TimbreFiscalDigital/@UUID"/>
               </fo:block>
            <fo:block font-size="4pt"> 
       </fo:block>
            <fo:block font-size="6pt" font-weight="bold" white-space-collapse="true" >Sello : 
              <xsl:value-of select="//TimbreFiscalDigital:TimbreFiscalDigital/@SelloSAT"/></fo:block>
            <fo:block font-size="3pt"> 
       </fo:block>
            <fo:block font-size="6pt" font-weight="bold" white-space-collapse="true" >Sello SAT: 
              <xsl:value-of select="//TimbreFiscalDigital:TimbreFiscalDigital/@SelloCFD"/></fo:block>
          </fo:block-container>
          <fo:block-container left="14.7cm" position="absolute" top="18.3cm" width="6cm">
            <fo:block font-size="7pt">
              <fo:table border-collapse="separate" table-layout="fixed" width="100%">
                <fo:table-column column-width="3.3cm"/>
                <fo:table-column column-width="2cm" text-align="right"/>
                <fo:table-body>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block>Subtotal</fo:block>
                    </fo:table-cell>
                    <fo:table-cell>
                      <fo:block>
                        <xsl:value-of select="format-number(@SubTotal, &quot;$###,###.00&quot;)"/>
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block>Traslados</fo:block>
                    </fo:table-cell>
                    <fo:table-cell>
                      <fo:block/>
                    </fo:table-cell>
                  </fo:table-row>
                  <xsl:apply-templates select="cfdi:Impuestos/cfdi:Traslados/cfdi:Traslado"/>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block>Retenciones</fo:block>
                    </fo:table-cell>
                    <fo:table-cell>
                      <fo:block/>
                    </fo:table-cell>
                  </fo:table-row>
                   <xsl:apply-templates select="cfdi:Impuestos/cfdi:Retenciones/cfdi:Retencion"/>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block>Descuentos</fo:block>
                    </fo:table-cell>
                    <fo:table-cell>
                      <fo:block>
                        <xsl:value-of select="format-number(@Descuento, &quot;$###,###.00&quot;)"/>
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block>Total</fo:block>
                    </fo:table-cell>
                    <fo:table-cell>
                      <fo:block>
                        <xsl:value-of select="format-number(@Total, &quot;$###,###.00&quot;)"/>
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                </fo:table-body>
              </fo:table>
            </fo:block>
          </fo:block-container>
          <fo:block-container left="0.4cm" position="absolute" top="23.8cm" width="19cm">
            <fo:block font-size="5.5pt" font-weight="bold" white-space-collapse="true">Cadena Orginal :
     |

              <xsl:apply-templates select="//TimbreFiscalDigital:TimbreFiscalDigital"/>
              ||</fo:block>
          </fo:block-container>
          <!-- pagare !-->
          <fo:block-container left="0.8cm" position="absolute" top="21.3cm" width="14.3cm">
            <fo:block font-size="6pt"/>
          </fo:block-container>
          <fo:block-container font-size="8pt" left="1cm" position="absolute" top="23.73cm" white-space-collapse="false" white-space-treatment="preserve">
            <fo:block text-align="left"/>
            <fo:block text-align="left"/>
          </fo:block-container>






<!--  pagare ! -->
<fo:block-container position="absolute" left="8.3cm" top="20.85cm">
  <fo:block font-size="9pt"> 
    No. Pagaré <xsl:value-of select="@Folio"/>
  </fo:block>
</fo:block-container>
<fo:block-container position="absolute" left="13.3cm" top="20.85cm">
<fo:block font-size="9pt"> Bueno por
            <xsl:value-of select="format-number(@Total, &quot;$###,###.00&quot;)"/>
</fo:block>
</fo:block-container>
<fo:block-container position="absolute" left="1cm" top="20.85cm">
<fo:block font-size="7pt">
Fecha:
<xsl:value-of select="substring(@Fecha,1,10)"/>
</fo:block>
</fo:block-container>
<fo:block-container position="absolute" left="0.8cm" top="21.3cm" width="14.3cm">
<fo:block font-size="6pt">
POR MEDIO DE ESTE PAGARÉ DEBEMOS Y PAGAREMOS INCONDICIONALMENTE A LA ORDEN DE
<xsl:value-of select="cfdi:Emisor/@Nombre"/>
EN ESTA PLAZA. LA CANTIDAD DE $
<xsl:value-of select="format-number(@Total, '##,###.00')"/>
(
              <xsl:value-of select="//cfdi:Addenda/clienteDatos/@totalenletra"/>
) EN LA FECHA
<xsl:value-of select="substring(@Fecha,1,10)"/>
GENERANDO EL 12% DE INTERÉS MENSUAL EN CASO DE NO PAGAR A LA FECHA ANTES SEÑALADA Y HASTA SU TOTAL LIQUIDACIÓN.
</fo:block>
</fo:block-container>
<fo:block-container position="absolute" left="0.7cm" top="22.53cm">
<fo:block font-size="6pt">Deudor:</fo:block>
</fo:block-container>
<fo:block-container position="absolute" left="0.7cm" top="23.3cm">
<fo:block font-size="6pt">Nombre:</fo:block>
</fo:block-container>
<fo:block-container position="absolute" left="10.7cm" top="23.3cm">
<fo:block font-size="6pt">Firma:</fo:block>
</fo:block-container>
<fo:block-container position="absolute" left="1.6cm" top="22.53cm">
<fo:block font-size="6pt">
<xsl:value-of select="cfdi:Receptor/@Nombre"/>
</fo:block>
</fo:block-container>
<fo:block-container position="absolute" left="1cm" top="25.73cm" font-size="8pt" white-space-collapse="false" white-space-treatment="preserve">
<fo:block text-align="left">
Datos para pago: Banco BBVA Bancomer, Sucursal 4199.
</fo:block>
<fo:block text-align="left">
Cuenta de cheques número 0155557275. Cuenta CLABE número 012010001555572756.
</fo:block>
</fo:block-container>





          <fo:block-container left="17cm" position="absolute" top="20.6cm" width="5.5cm">
            <fo:block>
              <fo:external-graphic content-height="3cm" content-width="3cm" src="url('http://localhost:8080/Codificador-war/faces/descargas?serie={@Serie}&amp;folio={@Folio}&amp;rfc={cfdi:Emisor/@Rfc}&amp;tipo=CBB')"/>
            </fo:block>
          </fo:block-container>
          <fo:block-container position="absolute" top="25cm">
            <fo:block font-size="7pt" font-weight="bold" text-align="center">Este documento es una representación impresa de un CFDI
      </fo:block>
          </fo:block-container>
          <!-- fin no trae la leyendas para cambiarla
       <fo:block font-size="10pt"><xsl:value-of select="@formaDePago"/></fo:block>!-->
        </fo:flow>
      </fo:page-sequence>
    </fo:root>
  </xsl:template>
  <xsl:template match="//cfdi:Concepto">
    <fo:table-row>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="@ClaveProdServ"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="@Cantidad"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="@ClaveUnidad"/>
           
            
          <xsl:value-of select="@Unidad"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="@Descripcion"/>
          <fo:table border-collapse="separate" table-layout="fixed" width="100%">
            <fo:table-column column-width="6cm"/>
            <fo:table-column column-width="4cm"/>
            <fo:table-header>
              <fo:table-cell>
                <fo:block font-weight="bold"/>
              </fo:table-cell>
              <fo:table-cell>
                <fo:block font-weight="bold"/>
              </fo:table-cell>
            </fo:table-header>
            <fo:table-body>
              <fo:table-row>
                <fo:table-cell>
                  <fo:block>
                     Traslados
                   </fo:block>
                </fo:table-cell>
              </fo:table-row>
              <xsl:apply-templates select="cfdi:Impuestos/cfdi:Traslados/cfdi:Traslado"/>
            </fo:table-body>
          </fo:table>
           <fo:table border-collapse="separate" table-layout="fixed" width="100%">
            <fo:table-column column-width="6cm"/>
            <fo:table-column column-width="4cm"/>
            <fo:table-header>
              <fo:table-cell>
                <fo:block font-weight="bold"/>
              </fo:table-cell>
              <fo:table-cell>
                <fo:block font-weight="bold"/>
              </fo:table-cell>
            </fo:table-header>
            <fo:table-body>
              <fo:table-row>
                <fo:table-cell>
                   <fo:block>
                     Retenciones
                   </fo:block>  
                </fo:table-cell>
              </fo:table-row>
              <xsl:apply-templates select="cfdi:Impuestos/cfdi:Retenciones/cfdi:Retencion"/>
            </fo:table-body>
          </fo:table>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell text-align="right">
        <fo:block text-align="right">
          <xsl:value-of select="format-number(@ValorUnitario, &quot;$###,###.00&quot;)"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell text-align="right">
        <fo:block text-align="right">
          <xsl:value-of select="format-number(@Importe, &quot;$###,###.00&quot;)"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell text-align="right">
        <fo:block text-align="right">
          <xsl:value-of select="format-number(@Descuento, &quot;$###,###.00&quot;)"/>
        </fo:block>
      </fo:table-cell>
    </fo:table-row>
  </xsl:template>
  <xsl:template match="//cfdi:DomicilioFiscal">
    <fo:block font-size="7pt">
      <xsl:value-of select="@Calle"/>#

      <xsl:value-of select="@NoExterior"/>
      - 

      <xsl:value-of select="@NoInterior"/></fo:block>
    <fo:block font-size="7pt">
      <xsl:value-of select="@Colonia"/>,
          
      <xsl:value-of select="@Municipio"/>
      ,
      <xsl:value-of select="@Estado"/></fo:block>
    <fo:block font-size="7pt">
      <xsl:value-of select="@Pais"/>,CP 
      <xsl:value-of select="@CodigoPostal"/>
      .</fo:block>
    <fo:block>Telefonos: (449)9903117
    </fo:block>
    <fo:block>http://www.invenio.mx    servicios@invenio.mx
    </fo:block>
    <fo:block>Regimen Fiscal: Persona Moral en régimen general
    </fo:block>
  </xsl:template>
  <xsl:template match="//cfdi:Domicilio">
    <fo:block font-size="8pt">
      <xsl:value-of select="@Calle"/>- 
      <xsl:value-of select="@NoExterior"/>
      - 
      <xsl:value-of select="@NoInterior"/></fo:block>
    <fo:block font-size="8pt">
      <xsl:value-of select="@Colonia"/>,
    
      <xsl:value-of select="@Municipio"/>
      , 
      <xsl:value-of select="@Estado"/></fo:block>
    <fo:block font-size="8pt">
      <xsl:value-of select="@Pais"/>, CP 
      <xsl:value-of select="@CodigoPostal"/>
      .</fo:block>
  </xsl:template>
  <xsl:template match="//cfdi:Impuestos/cfdi:Traslados/cfdi:Traslado">
    <fo:table-row>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="@Impuesto"/>&#160;  
          <xsl:value-of select="@TipoFactor"/>&#160;
          <xsl:value-of select="@TasaOCuota"/>&#160;
        </fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="format-number(@Importe, &quot;$###,###.00&quot;)"/>
        </fo:block>
      </fo:table-cell>
    </fo:table-row>
  </xsl:template>
  <xsl:template match="//cfdi:Impuestos/cfdi:Retenciones/cfdi:Retencion">
    <fo:table-row>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="@Impuesto"/>&#160;  
          <xsl:value-of select="@TipoFactor"/>&#160;
          <xsl:value-of select="@TasaOCuota"/>&#160;
        </fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="format-number(@Importe, &quot;$###,###.00&quot;)"/>
        </fo:block>
      </fo:table-cell>
    </fo:table-row>
  </xsl:template>
  <xsl:template name="Requerido">
    <xsl:param name="valor"/>|

    <xsl:call-template name="ManejaEspacios">
      <xsl:with-param name="s" select="$valor"/></xsl:call-template>
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