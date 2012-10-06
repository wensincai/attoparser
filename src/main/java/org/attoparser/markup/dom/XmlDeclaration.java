/*
 * =============================================================================
 * 
 *   Copyright (c) 2012, The ATTOPARSER team (http://www.attoparser.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.attoparser.markup.dom;


/**
 * <p>
 *   Models a XML Declaration in a attoDOM Document.
 * </p>
 * 
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 1.0
 *
 */
public final class XmlDeclaration extends Node {
    
    private static final long serialVersionUID = 8210232665354213283L;
    
    
    private final String version;
    private final String encoding;
    private final String standalone;

    


    XmlDeclaration(final String version, final String encoding, final String standalone,
            final int line, final int col) {
        
        super(line, col);

        this.version = version;
        this.encoding = encoding;
        this.standalone = standalone;
        
    }


    
    public String getVersion() {
        return this.version;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public String getStandalone() {
        return this.standalone;
    }

    
    
    
    
    public final void visit(final AttoDOMVisitor visitor)
            throws AttoDOMVisitorException {
        visitor.visitXmlDeclaration(this);
    }
    
    
}
