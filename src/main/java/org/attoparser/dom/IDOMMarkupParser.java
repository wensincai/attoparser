/*
 * =============================================================================
 * 
 *   Copyright (c) 2012-2014, The ATTOPARSER team (http://www.attoparser.org)
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
package org.attoparser.dom;

import java.io.Reader;

import org.attoparser.ParseException;


/**
 *
 * @author Daniel Fern&aacute;ndez
 *
 * @since 2.0.0
 *
 */
public interface IDOMMarkupParser {



    /**
     * <p>
     *   Parse a document and convert it into a DOM tree, using the classes at the
     *   <tt>org.attoparser.dom</tt> package.
     * </p>
     *
     * @param document the document to be parsed, as a String.
     * @return the {@link org.attoparser.dom.Document} object resulting from parsing.
     * @throws ParseException if the document cannot be parsed.
     */
    public Document parse(final String document)
            throws ParseException;


    /**
     * <p>
     *   Parse a document and convert it into a DOM tree, using the classes at the
     *   <tt>org.attoparser.dom</tt> package.
     * </p>
     *
     * @param document the document to be parsed, as a char[].
     * @return the {@link org.attoparser.dom.Document} object resulting from parsing.
     * @throws ParseException if the document cannot be parsed.
     */
    public Document parse(final char[] document)
            throws ParseException;


    /**
     * <p>
     *   Parse a document and convert it into a DOM tree, using the classes at the
     *   <tt>org.attoparser.dom</tt> package.
     * </p>
     *
     * @param document the document to be parsed, as a char[].
     * @param offset the offset to be applied on the char[] document to determine the
     *        start of the document contents.
     * @param len the length (in chars) of the document stored in the char[].
     * @return the {@link org.attoparser.dom.Document} object resulting from parsing.
     * @throws ParseException if the document cannot be parsed.
     */
    public Document parse(final char[] document, final int offset, final int len)
            throws ParseException;


    /**
     * <p>
     *   Parse a document and convert it into a DOM tree, using the classes at the
     *   <tt>org.attoparser.dom</tt> package.
     * </p>
     * <p>
     *   Implementations of this interface must close the provided {@link Reader}
     *   object after parsing.
     * </p>
     *
     * @param reader a Reader on the document.
     * @return the {@link org.attoparser.dom.Document} object resulting from parsing.
     * @throws ParseException if the document cannot be parsed.
     */
    public Document parse(final Reader reader)
            throws ParseException;




    /**
     * <p>
     *   Parse a document and convert it into a DOM tree, using the classes at the
     *   <tt>org.attoparser.dom</tt> package.
     * </p>
     *
     * @param documentName the name of the document to be parsed.
     * @param document the document to be parsed, as a String.
     * @return the {@link org.attoparser.dom.Document} object resulting from parsing.
     * @throws ParseException if the document cannot be parsed.
     */
    public Document parse(final String documentName, final String document)
            throws ParseException;


    /**
     * <p>
     *   Parse a document and convert it into a DOM tree, using the classes at the
     *   <tt>org.attoparser.dom</tt> package.
     * </p>
     *
     * @param documentName the name of the document to be parsed.
     * @param document the document to be parsed, as a char[].
     * @return the {@link org.attoparser.dom.Document} object resulting from parsing.
     * @throws ParseException if the document cannot be parsed.
     */
    public Document parse(final String documentName, final char[] document)
            throws ParseException;


    /**
     * <p>
     *   Parse a document and convert it into a DOM tree, using the classes at the
     *   <tt>org.attoparser.dom</tt> package.
     * </p>
     *
     * @param documentName the name of the document to be parsed.
     * @param document the document to be parsed, as a char[].
     * @param offset the offset to be applied on the char[] document to determine the
     *        start of the document contents.
     * @param len the length (in chars) of the document stored in the char[].
     * @return the {@link org.attoparser.dom.Document} object resulting from parsing.
     * @throws ParseException if the document cannot be parsed.
     */
    public Document parse(final String documentName, final char[] document, final int offset, final int len)
            throws ParseException;


    /**
     * <p>
     *   Parse a document and convert it into a DOM tree, using the classes at the
     *   <tt>org.attoparser.dom</tt> package.
     * </p>
     * <p>
     *   Implementations of this interface must close the provided {@link Reader}
     *   object after parsing.
     * </p>
     *
     * @param documentName the name of the document to be parsed.
     * @param reader a Reader on the document.
     * @return the {@link org.attoparser.dom.Document} object resulting from parsing.
     * @throws ParseException if the document cannot be parsed.
     */
    public Document parse(final String documentName, final Reader reader)
            throws ParseException;

}
