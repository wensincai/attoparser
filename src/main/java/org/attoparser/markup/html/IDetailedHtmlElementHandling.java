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
package org.attoparser.markup.html;

import org.attoparser.AttoParseException;
import org.attoparser.IAttoHandleResult;
import org.attoparser.markup.html.elements.IHtmlElement;


/**
 * <p>
 *   Common interface for all handlers that support reporting detailed HTML parsing events.
 * </p>
 * 
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 2.0.0
 *
 */
public interface IDetailedHtmlElementHandling extends IDetailedHtmlAttributeSequenceHandling {

    

    /**
     * <p>
     *   Called when the start of a standalone element is found.
     * </p>
     * <p>
     *   A standalone element can be either a minimized tag or not. 
     *   For example: <tt>&lt;img src="..." /&gt;</tt> is minimized (self-closed), as opposed to 
     *   <tt>&lt;img src="..."&gt;</tt> (non-minimized, perfectly valid from the HTML 
     *   but not from the XML or XHTML standpoints).
     * </p>
     * <p>
     *   Artifacts are reported using the document <tt>buffer</tt> directly, and this buffer 
     *   should not be considered to be immutable, so reported structures should be copied if they need
     *   to be stored (either by copying <tt>len</tt> chars from the buffer <tt>char[]</tt> starting
     *   in <tt>offset</tt> or by creating a <tt>String</tt> from it using the same specification). 
     * </p>
     * <p>
     *   <b>Implementations of this handler should never modify the document buffer.</b> 
     * </p>
     * 
     * @param element the {@link IHtmlElement} element object representing the corresponding HTML element.
     * @param minimized whether the tag representing this element is minimized (self-closed) or not.
     * @param buffer the document buffer (not copied).
     * @param nameOffset the offset (position in buffer) where the element name appears.
     * @param nameLen the length (in chars) of the element name.
     * @param line the line in the original document where this artifact starts.
     * @param col the column in the original document where this artifact starts.
     * @return the result of handling the event, or null if no relevant result has to be returned.
     * @throws AttoParseException
     */
    public IAttoHandleResult handleHtmlStandaloneElementStart(
            final IHtmlElement element,
            final boolean minimized,
            final char[] buffer, 
            final int nameOffset, final int nameLen,
            final int line, final int col)
            throws AttoParseException;
    
    /**
     * <p>
     *   Called when the end of a standalone element is found.
     * </p>
     * 
     * @param element the {@link IHtmlElement} element object representing the corresponding HTML element.
     * @param minimized whether the tag representing this element is minimized (self-closed) or not.
     * @param buffer the document buffer (not copied).
     * @param nameOffset the offset (position in buffer) where the element name appears.
     * @param nameLen the length (in chars) of the element name.
     * @param line the line in the original document where this artifact starts.
     * @param col the column in the original document where this artifact starts.
     * @return the result of handling the event, or null if no relevant result has to be returned.
     * @throws AttoParseException
     */
    public IAttoHandleResult handleHtmlStandaloneElementEnd(
            final IHtmlElement element,
            final boolean minimized,
            final char[] buffer,
            final int nameOffset, final int nameLen,
            final int line, final int col)
            throws AttoParseException;

    
    

    /**
     * <p>
     *   Called when the start of an open element (an <i>open tag</i>) is found.
     * </p>
     * <p>
     *   Artifacts are reported using the document <tt>buffer</tt> directly, and this buffer 
     *   should not be considered to be immutable, so reported structures should be copied if they need
     *   to be stored (either by copying <tt>len</tt> chars from the buffer <tt>char[]</tt> starting
     *   in <tt>offset</tt> or by creating a <tt>String</tt> from it using the same specification). 
     * </p>
     * <p>
     *   <b>Implementations of this handler should never modify the document buffer.</b> 
     * </p>
     * 
     * @param element the {@link IHtmlElement} element object representing the corresponding HTML element.
     * @param buffer the document buffer (not copied).
     * @param nameOffset the offset (position in buffer) where the element name appears.
     * @param nameLen the length (in chars) of the element name.
     * @param line the line in the original document where this artifact starts.
     * @param col the column in the original document where this artifact starts.
     * @return the result of handling the event, or null if no relevant result has to be returned.
     * @throws AttoParseException
     */
    public IAttoHandleResult handleHtmlOpenElementStart(
            final IHtmlElement element,
            final char[] buffer, 
            final int nameOffset, final int nameLen,
            final int line, final int col)
            throws AttoParseException;
    
    /**
     * <p>
     *   Called when the end of an open element (an <i>open tag</i>) is found.
     * </p>
     * 
     * @param element the {@link IHtmlElement} element object representing the corresponding HTML element.
     * @param buffer the document buffer (not copied).
     * @param nameOffset the offset (position in buffer) where the element name appears.
     * @param nameLen the length (in chars) of the element name.
     * @param line the line in the original document where this artifact starts.
     * @param col the column in the original document where this artifact starts.
     * @return the result of handling the event, or null if no relevant result has to be returned.
     * @throws AttoParseException
     */
    public IAttoHandleResult handleHtmlOpenElementEnd(
            final IHtmlElement element,
            final char[] buffer,
            final int nameOffset, final int nameLen,
            final int line, final int col)
            throws AttoParseException;

    

    
    
    
    /**
     * <p>
     *   Called when the start of a close element (a <i>close tag</i>) is found.
     * </p>
     * <p>
     *   Artifacts are reported using the document <tt>buffer</tt> directly, and this buffer 
     *   should not be considered to be immutable, so reported structures should be copied if they need
     *   to be stored (either by copying <tt>len</tt> chars from the buffer <tt>char[]</tt> starting
     *   in <tt>offset</tt> or by creating a <tt>String</tt> from it using the same specification). 
     * </p>
     * <p>
     *   <b>Implementations of this handler should never modify the document buffer.</b> 
     * </p>
     * 
     * @param element the {@link IHtmlElement} element object representing the corresponding HTML element.
     * @param buffer the document buffer (not copied).
     * @param nameOffset the offset (position in buffer) where the element name appears.
     * @param nameLen the length (in chars) of the element name.
     * @param line the line in the original document where this artifact starts.
     * @param col the column in the original document where this artifact starts.
     * @return the result of handling the event, or null if no relevant result has to be returned.
     * @throws AttoParseException
     */
    public IAttoHandleResult handleHtmlCloseElementStart(
            final IHtmlElement element,
            final char[] buffer, 
            final int nameOffset, final int nameLen,
            final int line, final int col)
            throws AttoParseException;
    
    /**
     * <p>
     *   Called when the end of a close element (a <i>close tag</i>) is found.
     * </p>
     * 
     * @param element the {@link IHtmlElement} element object representing the corresponding HTML element.
     * @param buffer the document buffer (not copied).
     * @param nameOffset the offset (position in buffer) where the element name appears.
     * @param nameLen the length (in chars) of the element name.
     * @param line the line in the original document where this artifact starts.
     * @param col the column in the original document where this artifact starts.
     * @return the result of handling the event, or null if no relevant result has to be returned.
     * @throws AttoParseException
     */
    public IAttoHandleResult handleHtmlCloseElementEnd(
            final IHtmlElement element,
            final char[] buffer,
            final int nameOffset, final int nameLen,
            final int line, final int col)
            throws AttoParseException;


    /**
     * <p>
     *   Called when the start of an auto-close element (a <i>close tag</i> not present in original markup,
     *   that is automatically inserted for keeping element stacks valid) is found.
     * </p>
     * <p>
     *   Artifacts are reported using the document <tt>buffer</tt> directly, and this buffer
     *   should not be considered to be immutable, so reported structures should be copied if they need
     *   to be stored (either by copying <tt>len</tt> chars from the buffer <tt>char[]</tt> starting
     *   in <tt>offset</tt> or by creating a <tt>String</tt> from it using the same specification).
     * </p>
     * <p>
     *   <b>Implementations of this handler should never modify the document buffer.</b>
     * </p>
     *
     * @param element the {@link IHtmlElement} element object representing the corresponding HTML element.
     * @param buffer the document buffer (not copied).
     * @param nameOffset the offset (position in buffer) where the element name appears.
     * @param nameLen the length (in chars) of the element name.
     * @param line the line in the original document where this artifact starts.
     * @param col the column in the original document where this artifact starts.
     * @return the result of handling the event, or null if no relevant result has to be returned.
     * @throws AttoParseException
     * @since 2.0.0
     */
    public IAttoHandleResult handleHtmlAutoCloseElementStart(
            final IHtmlElement element,
            final char[] buffer,
            final int nameOffset, final int nameLen,
            final int line, final int col)
            throws AttoParseException;

    /**
     * <p>
     *   Called when the end of a auto-close element (a <i>close tag</i> not present in original markup,
     *   that is automatically inserted for keeping element stacks valid) is found.
     * </p>
     *
     * @param element the {@link IHtmlElement} element object representing the corresponding HTML element.
     * @param buffer the document buffer (not copied).
     * @param nameOffset the offset (position in buffer) where the element name appears.
     * @param nameLen the length (in chars) of the element name.
     * @param line the line in the original document where this artifact starts.
     * @param col the column in the original document where this artifact starts.
     * @return the result of handling the event, or null if no relevant result has to be returned.
     * @throws AttoParseException
     * @since 2.0.0
     */
    public IAttoHandleResult handleHtmlAutoCloseElementEnd(
            final IHtmlElement element,
            final char[] buffer,
            final int nameOffset, final int nameLen,
            final int line, final int col)
            throws AttoParseException;


    /**
     * <p>
     *   Called when the start of an unmatched close element (a <i>close tag</i> appearing in original markup
     *   but not corresponding to any open tags) is found.
     * </p>
     * <p>
     *   Artifacts are reported using the document <tt>buffer</tt> directly, and this buffer
     *   should not be considered to be immutable, so reported structures should be copied if they need
     *   to be stored (either by copying <tt>len</tt> chars from the buffer <tt>char[]</tt> starting
     *   in <tt>offset</tt> or by creating a <tt>String</tt> from it using the same specification).
     * </p>
     * <p>
     *   <b>Implementations of this handler should never modify the document buffer.</b>
     * </p>
     *
     *
     * @param element the {@link IHtmlElement} element object representing the corresponding HTML element.
     * @param buffer the document buffer (not copied).
     * @param nameOffset the offset (position in buffer) where the element name appears.
     * @param nameLen the length (in chars) of the element name.
     * @param line the line in the original document where this artifact starts.
     * @param col the column in the original document where this artifact starts.
     * @return the result of handling the event, or null if no relevant result has to be returned.
     * @throws AttoParseException
     * @since 2.0.0
     */
    public IAttoHandleResult handleHtmlUnmatchedCloseElementStart(
            final IHtmlElement element,
            final char[] buffer,
            final int nameOffset, final int nameLen,
            final int line, final int col)
            throws AttoParseException;

    /**
     * <p>
     *   Called when the end of a unmatched close element (a <i>close tag</i> appearing in original markup
     *   but not corresponding to any open tags) is found.
     * </p>
     *
     * @param element the {@link IHtmlElement} element object representing the corresponding HTML element.
     * @param buffer the document buffer (not copied).
     * @param nameOffset the offset (position in buffer) where the element name appears.
     * @param nameLen the length (in chars) of the element name.
     * @param line the line in the original document where this artifact starts.
     * @param col the column in the original document where this artifact starts.
     * @return the result of handling the event, or null if no relevant result has to be returned.
     * @throws AttoParseException
     * @since 2.0.0
     */
    public IAttoHandleResult handleHtmlUnmatchedCloseElementEnd(
            final IHtmlElement element,
            final char[] buffer,
            final int nameOffset, final int nameLen,
            final int line, final int col)
            throws AttoParseException;

    
}
