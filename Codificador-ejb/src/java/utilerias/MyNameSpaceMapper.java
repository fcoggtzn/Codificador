/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 *
 * @author franciscogutierrez
 */
public  class MyNameSpaceMapper extends NamespacePrefixMapper {

    private static final String FOO_PREFIX = "cfdi"; // DEFAULT NAMESPACE
    private static final String FOO_URI = "http://www.sat.gob.mx/cfd/3";

    private static final String BAR_PREFIX = "cfdi";
    private static final String BAR_URI = "http://www.sat.gob.mx/cfd/3";

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if(FOO_URI.equals(namespaceUri)) {
            return FOO_PREFIX;
        } else if(BAR_URI.equals(namespaceUri)) {
            return BAR_PREFIX;
        }
        return suggestion;
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[] { FOO_URI, BAR_URI };
    }

}