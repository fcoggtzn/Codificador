/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author franciscogutierrez
 */
public  class MyNameSpaceMapperPago extends NamespacePrefixMapper {
    
    
     private Map<String, String> namespaceMap = new HashMap<>();
 
	/**
	 * Create mappings.
	 */
	public MyNameSpaceMapperPago() {
		namespaceMap.put("http://www.sat.gob.mx/cfd/3", "cfdi");
		namespaceMap.put("http://www.sat.gob.mx/Pagos", "pago10");
	}
 
	/* (non-Javadoc)
	 * Returning null when not found based on spec.
	 * @see com.sun.xml.bind.marshaller.NamespacePrefixMapper#getPreferredPrefix(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		return namespaceMap.getOrDefault(namespaceUri, suggestion);
	}
    
}