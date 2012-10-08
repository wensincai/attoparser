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

import java.util.Map;

import org.attoparser.AttoParseException;
import org.attoparser.markup.AbstractStandardMarkupAttoHandler;
import org.attoparser.markup.DocumentRestrictions;




/**
 * <p>
 *   A Comment node in a attoDOM tree.
 * </p>
 * 
 * 
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 1.0
 *
 */
public final class DOMMarkupAttoHandler extends AbstractStandardMarkupAttoHandler {

    
    private Document document = null;
    private boolean parsingFinished = false;
    private long parsingStartTimeNanos = -1L;
    private long parsingEndTimeNanos = -1L;
    private long parsingTotalTimeNanos = -1L;

    private Element currentElement = null;
    

    
    public DOMMarkupAttoHandler() {
        // Must be well-formed in order to create an adequate DOM tree
        super(DocumentRestrictions.wellFormed());
    }

    
    
    public Document getDocument() {
        return this.document;
    }

    
    
    public long getParsingStartTimeNanos() {
        return this.parsingStartTimeNanos;
    }

    public long getParsingEndTimeNanos() {
        return this.parsingEndTimeNanos;
    }

    public long getParsingTotalTimeNanos() {
        return this.parsingTotalTimeNanos;
    }
    
    public boolean isParsingFinished() {
        return this.parsingFinished;
    }


    

    @Override
    public void handleDocumentStart(
            final long startTimeNanos, final boolean requireWellFormed) 
            throws AttoParseException {
        
        super.handleDocumentStart(startTimeNanos, requireWellFormed);
        
        this.document = new Document();
        this.parsingStartTimeNanos = startTimeNanos;
        
    }

    
    
    @Override
    public void handleDocumentEnd(
            final long endTimeNanos, final long totalTimeNanos,
            final boolean requireWellFormed)
            throws AttoParseException {

        super.handleDocumentEnd(endTimeNanos, totalTimeNanos, requireWellFormed);
        
        this.parsingEndTimeNanos = endTimeNanos;
        this.parsingTotalTimeNanos = totalTimeNanos;
        
        this.parsingFinished = true;
        
    }

    
    
    
    
    @Override
    public void handleXmlDeclaration(
            final String version, final String encoding, final String standalone, 
            final int line, final int col) 
            throws AttoParseException {

        super.handleXmlDeclaration(version, encoding, standalone, line, col);
        
        final XmlDeclaration xmlDeclaration = new XmlDeclaration(version, encoding, standalone, line, col);
        
        if (this.currentElement == null) {
            this.document.addRootNode(xmlDeclaration);
        } else {
            this.currentElement.addChild(xmlDeclaration);
        }
        
    }

    
    
    @Override
    public void handleDocType(
            final String elementName, 
            final String publicId, final String systemId, final String internalSubset, 
            final int line, final int col)
            throws AttoParseException {

        super.handleDocType(elementName, publicId, systemId, internalSubset, line, col);

        final DocType docType = new DocType(elementName, publicId, systemId, internalSubset, line, col);
        
        if (this.currentElement == null) {
            this.document.addRootNode(docType);
        } else {
            this.currentElement.addChild(docType);
        }
        
    }

    
    
    
    
    @Override
    public void handleStandaloneElement(
            final String elementName, final Map<String, String> attributes, 
            final int line, final int col)
            throws AttoParseException {

        super.handleStandaloneElement(elementName, attributes, line, col);

        final Element element = new Element(elementName, true, line, col);
        element.addAttributes(attributes);
        
        if (this.currentElement == null) {
            this.document.addRootNode(element);
        } else {
            this.currentElement.addChild(element);
        }
        
    }

    
    
    @Override
    public void handleOpenElement(
            final String elementName, final Map<String, String> attributes, 
            final int line, final int col)
            throws AttoParseException {

        super.handleOpenElement(elementName, attributes, line, col);

        final Element element = new Element(elementName, false, line, col);
        element.addAttributes(attributes);
        
        if (this.currentElement == null) {
            this.document.addRootNode(element);
        } else {
            this.currentElement.addChild(element);
        }
        this.currentElement = element;
        
    }


    
    @Override
    public void handleCloseElement(
            final String elementName, 
            final int line, final int col)
            throws AttoParseException {

        super.handleCloseElement(elementName, line, col);

        this.currentElement = this.currentElement.parent;
        
    }

    
    

    
    
    @Override
    public void handleComment(
            final char[] buffer, final int offset, final int len, 
            final int line, final int col) 
            throws AttoParseException {

        super.handleComment(buffer, offset, len, line, col);
        
        final Comment comment = new Comment(new String(buffer, offset, len), line, col);
        
        if (this.currentElement == null) {
            this.document.addRootNode(comment);
        } else {
            this.currentElement.addChild(comment);
        }
        
    }
    
    

    @Override
    public void handleCDATASection(
            final char[] buffer, final int offset, final int len,
            final int line, final int col) 
            throws AttoParseException {

        super.handleCDATASection(buffer, offset, len, line, col);
        
        final CDATASection cdataSection = new CDATASection(new String(buffer, offset, len), line, col);
        
        if (this.currentElement == null) {
            this.document.addRootNode(cdataSection);
        } else {
            this.currentElement.addChild(cdataSection);
        }
        
    }
    
    

    @Override
    public void handleText(
            final char[] buffer, final int offset, final int len, 
            final int line, final int col)
            throws AttoParseException {

        super.handleText(buffer, offset, len, line, col);
        
        final Text text = new Text(new String(buffer, offset, len), line, col);
        
        if (this.currentElement == null) {
            this.document.addRootNode(text);
        } else {
            this.currentElement.addChild(text);
        }
        
    }



    
    
    @Override
    public void handleProcessingInstruction(
            final String target, final String content,
            final int line, final int col) 
            throws AttoParseException {

        super.handleProcessingInstruction(target, content, line, col);
        
        final ProcessingInstruction processingInstruction = 
                new ProcessingInstruction(target, content, line, col);
        
        if (this.currentElement == null) {
            this.document.addRootNode(processingInstruction);
        } else {
            this.currentElement.addChild(processingInstruction);
        }
        
    }
    
}
