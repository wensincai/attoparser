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
package org.attoparser.markup;

import org.attoparser.AbstractAttoHandler;
import org.attoparser.AttoParseException;





/**
 * 
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 1.0
 *
 */
public abstract class AbstractMarkupAttoHandler 
        extends AbstractAttoHandler
        implements IElementHandling, IDocTypeHandling, ICdataHandling, ICommentHandling {


    
    protected AbstractMarkupAttoHandler() {
        super();
    }



    @Override
    public final void structure(
            final char[] buffer,
            final int offset, final int len, 
            final int line, final int col)
            throws AttoParseException {
        
        if (!ElementMarkupParsingUtil.tryParseElement(buffer, offset, len, line, col, this)) {
            if (!CommentMarkupParsingUtil.tryParseComment(buffer, offset, len, line, col, this)) {
                 if (!CdataMarkupParsingUtil.tryParseCdata(buffer, offset, len, line, col, this )) {
                     if (!DocTypeMarkupParsingUtil.tryParseDocType(buffer, offset, len, line, col, this)) {
                         throw new AttoParseException(
                                 "Could not parse as markup structure: " +
                                 "\"" + new String(buffer, offset, len) + "\"", 
                                 line, col);
                     }
                 }
            }
        }

    }



    public void docType(
            final char[] buffer, 
            final int contentOffset, final int contentLen,
            final int outerOffset, final int outerLen, 
            final int line, final int col)
            throws AttoParseException {
        // Nothing to be done here, meant to be overridden if required
    }
    
    
    
    public void standaloneElement(
            final char[] buffer, 
            final int contentOffset, final int contentLen,
            final int outerOffset, final int outerLen, 
            final int line, final int col)
            throws AttoParseException {
        // Nothing to be done here, meant to be overridden if required
    }


    public void openElement(
            final char[] buffer, 
            final int contentOffset, final int contentLen,
            final int outerOffset, final int outerLen,
            final int line, final int col)
            throws AttoParseException {
        // Nothing to be done here, meant to be overridden if required
    }

    
    public void closeElement(
            final char[] buffer, 
            final int contentOffset, final int contentLen,
            final int outerOffset, final int outerLen, 
            final int line, final int col)
            throws AttoParseException {
        // Nothing to be done here, meant to be overridden if required
    }

    
    public void comment(
            final char[] buffer, 
            final int contentOffset, final int contentLen,
            final int outerOffset, final int outerLen, 
            final int line, final int col)
            throws AttoParseException {
        // Nothing to be done here, meant to be overridden if required
    }

    
    public void cdata(
            final char[] buffer, 
            final int contentOffset, final int contentLen,
            final int outerOffset, final int outerLen, 
            final int line, final int col)
            throws AttoParseException {
        // Nothing to be done here, meant to be overridden if required
    }


    
    
}