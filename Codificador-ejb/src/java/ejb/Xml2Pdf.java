/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ejb.Stateless;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;

/**
 *
 * @author franciscogutierrez
 */
@Stateless
public class Xml2Pdf implements Xml2PdfLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
     @Override
      public byte[] generatePDF(byte[] archivoXslt,byte[] archivoXml){
     try {
	            System.out.println("FOP ExampleXML2PDF\n");
	            System.out.println("Preparing...");
	
	            // Setup directories
	            File baseDir = new File(".");
	            File outDir = new File(baseDir, "out");
	            outDir.mkdirs();
                    
                    FileOutputStream xmlf = new FileOutputStream("xml");
                    xmlf.write(archivoXml);
                    xmlf.close();
                    
                    FileOutputStream xslf = new FileOutputStream("xslt");
                    xslf.write(archivoXslt);
                    xslf.close();
	
	            // Setup input and output files
	            File xmlfile = new File("xml");
	            File xsltfile = new File("xslt");
                    File pdffile = new File(outDir, "ResultXML2PDF.pdf");
	
	            System.out.println("Input: XML (" + xmlfile + ")");
	            System.out.println("Stylesheet: " + xsltfile);
	            System.out.println("Output: PDF (" + pdffile + ")");
	            System.out.println();
	            System.out.println("Transforming...");
	
	            // configure fopFactory as desired
	            final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
	
	            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
	            OutputStream out = new java.io.FileOutputStream(pdffile);
	            out = new java.io.BufferedOutputStream(out);
	
	            try {
                // Construct fop with desired output format
	                Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
	
	                // Setup XSLT
	                TransformerFactory factory = TransformerFactory.newInstance();
	                Transformer transformer = factory.newTransformer(new StreamSource(xsltfile));
	
	                // Set the value of a <param> in the stylesheet
	                transformer.setParameter("versionParam", "2.0");
	
	                // Setup input for XSLT transformation
	                Source src = new StreamSource(xmlfile);
	
	                // Resulting SAX events (the generated FO) must be piped through to FOP
	                Result res = new SAXResult(fop.getDefaultHandler());
	
	                // Start XSLT transformation and FOP processing
	                transformer.transform(src, res);
	            } finally {
	                out.close();
	            }
	            
	            System.out.println("Success!");
                    return read(pdffile);
	        } catch (IOException | TransformerException | FOPException e) {
	            e.printStackTrace(System.err);
	            System.exit(-1);
	        }
              return null;

}
      
      
private byte[] read(File file) throws IOException  {    

    byte[] buffer = new byte[(int) file.length()];
    InputStream ios = null;
    try {
        ios = new FileInputStream(file);
        if (ios.read(buffer) == -1) {
            throw new IOException(
                    "EOF reached while trying to read the whole file");
        }
    } finally {
        try {
            if (ios != null)
                ios.close();
        } catch (IOException e) {
        }
    }
    return buffer;
}
}
