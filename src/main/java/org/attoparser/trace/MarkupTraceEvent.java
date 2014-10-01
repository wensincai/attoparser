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
package org.attoparser.trace;

import java.util.Arrays;


/**
 * 
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 1.2
 *
 */
public abstract class MarkupTraceEvent {

    public static enum EventType {

        DOCUMENT_START("DS"), DOCUMENT_END("DE"),

        STANDALONE_ELEMENT_START("SES"), STANDALONE_ELEMENT_END("SEE"),
        NON_MINIMIZED_STANDALONE_ELEMENT_START("NSES"), NON_MINIMIZED_STANDALONE_ELEMENT_END("NSEE"),

        OPEN_ELEMENT_START("OES"), OPEN_ELEMENT_END("OEE"),

        CLOSE_ELEMENT_START("CES"), CLOSE_ELEMENT_END("CEE"),
        AUTO_CLOSE_ELEMENT_START("ACES"), AUTO_CLOSE_ELEMENT_END("ACEE"),
        UNMATCHED_CLOSE_ELEMENT_START("UCES"), UNMATCHED_CLOSE_ELEMENT_END("UCEE"),

        ATTRIBUTE("A"), INNER_WHITE_SPACE("IWS"),

        TEXT("T"), COMMENT("C"), CDATA_SECTION("CD"), XML_DECLARATION("XD"), DOC_TYPE("DT"), PROCESSING_INSTRUCTION("P");


        private String stringRepresentation;

        private EventType(final String stringRepresentation) {
            this.stringRepresentation = stringRepresentation;
        }

        public String toString() {
            return this.stringRepresentation;
        }

    }


    private final EventType eventType;
    final String[] contents;
    final int[] lines;
    final int[] cols;




    
    

    private MarkupTraceEvent(final EventType eventType, final int[] lines, final int[] cols, final String... contents) {

        super();

        if (eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null");
        }

        this.eventType = eventType;
        this.contents = contents;
        this.lines = lines;
        this.cols = cols;

    }


    public EventType getEventType() {
        return this.eventType;
    }




    @Override
    public String toString() {

        final StringBuilder strBuilder = new StringBuilder();

        strBuilder.append(this.eventType);

        if (this.contents != null && this.lines != null & this.lines.length == this.contents.length) {

            for (int i = 0; i < this.contents.length; i++) {
                strBuilder.append('(');
                if (this.contents[i] != null) {
                    strBuilder.append(this.contents[i]);
                }
                strBuilder.append(')');
                strBuilder.append('{');
                strBuilder.append(String.valueOf(this.lines[i]));
                strBuilder.append(',');
                strBuilder.append(String.valueOf(this.cols[i]));
                strBuilder.append('}');
            }
            
            return strBuilder.toString();
            
        }

        
        if (this.contents != null) {
            for (final String contentItem : this.contents) {
                strBuilder.append('(');
                if (contentItem != null) {
                    strBuilder.append(contentItem);
                }
                strBuilder.append(')');
            }
        }

        strBuilder.append('{');
        strBuilder.append(String.valueOf(this.lines[0]));
        strBuilder.append(',');
        strBuilder.append(String.valueOf(this.cols[0]));
        strBuilder.append('}');

        return strBuilder.toString();

    }





    boolean matchesTypeAndContent(final MarkupTraceEvent event) {
        if (this == event) {
            return true;
        }
        if (event == null) {
            return false;
        }
        if (this.eventType == null) {
            if (event.eventType != null) {
                return false;
            }
        } else if (!this.eventType.equals(event.eventType)) {
            return false;
        }
        if (this.contents == null) {
            if (event.contents != null) {
                return false;
            }
        } else if (!Arrays.equals(this.contents, event.contents)) {
            return false;
        }
        return true;
    }




    @Override
    public int hashCode() {
        int result = eventType.hashCode();
        result = 31 * result + Arrays.hashCode(contents);
        result = 31 * result + Arrays.hashCode(lines);
        result = 31 * result + Arrays.hashCode(cols);
        return result;
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final MarkupTraceEvent that = (MarkupTraceEvent) o;

        if (!Arrays.equals(cols, that.cols)) {
            return false;
        }
        if (!Arrays.equals(contents, that.contents)) {
            return false;
        }
        if (!Arrays.equals(lines, that.lines)) {
            return false;
        }
        if (eventType != that.eventType) {
            return false;
        }

        return true;

    }






    public static final class DocumentStartTraceEvent extends MarkupTraceEvent {

        public DocumentStartTraceEvent(final long startTimeNanos, final int line, final int col) {
            super(EventType.DOCUMENT_START, new int[] {line}, new int[] {col}, String.valueOf(startTimeNanos));
        }

        public long getStartTimeNanos() {
            return Long.parseLong(this.contents[0]);
        }

        public int getLine() {
            return this.lines[0];
        }

        public int getCol() {
            return this.cols[0];
        }

    }

    public static final class DocumentEndTraceEvent extends MarkupTraceEvent {

        public DocumentEndTraceEvent(final long endTimeNanos, final long totalTimeNanos, final int line, final int col) {
            super(EventType.DOCUMENT_END, new int[] {line}, new int[] {col}, String.valueOf(endTimeNanos), String.valueOf(totalTimeNanos));
        }

        public long getStartTimeNanos() {
            return Long.parseLong(this.contents[0]);
        }

        public long getTotalTimeNanos() {
            return Long.parseLong(this.contents[1]);
        }

        public int getLine() {
            return this.lines[0];
        }

        public int getCol() {
            return this.cols[0];
        }

    }

    static abstract class AbstractContentTraceEvent extends MarkupTraceEvent {

        protected AbstractContentTraceEvent(final EventType type, final String content, final int line, final int col) {
            super(type, new int[] {line}, new int[] {col}, content);
            if (content == null) {
                throw new IllegalArgumentException("Contentn cannot be null");
            }
        }

        public String getContent() {
            return this.contents[0];
        }

        public int getLine() {
            return this.lines[0];
        }

        public int getCol() {
            return this.cols[0];
        }

    }

    public static final class TextTraceEvent extends AbstractContentTraceEvent {
        public TextTraceEvent(final String content, final int line, final int col) {
            super(EventType.TEXT, content, line, col);
        }
    }

    public static final class CommentTraceEvent extends AbstractContentTraceEvent {
        public CommentTraceEvent(final String content, final int line, final int col) {
            super(EventType.COMMENT, content, line, col);
        }
    }

    public static final class CDATASectionTraceEvent extends AbstractContentTraceEvent {
        public CDATASectionTraceEvent(final String content, final int line, final int col) {
            super(EventType.CDATA_SECTION, content, line, col);
        }
    }

    public static final class InnerWhiteSpaceTraceEvent extends AbstractContentTraceEvent {
        public InnerWhiteSpaceTraceEvent(final String content, final int line, final int col) {
            super(EventType.INNER_WHITE_SPACE, content, line, col);
        }
    }

    static abstract class AbstractElementTraceEvent extends MarkupTraceEvent {

        protected AbstractElementTraceEvent(final EventType type, final String elementName, final int line, final int col) {
            super(type, new int[] {line}, new int[] {col}, elementName);
            if (elementName == null || elementName.trim().equals("")) {
                throw new IllegalArgumentException("Element name cannot be null or empty");
            }
        }

        public String getElementName() {
            return this.contents[0];
        }

        public int getLine() {
            return this.lines[0];
        }

        public int getCol() {
            return this.cols[0];
        }

    }

    public static final class StandaloneElementStartTraceEvent extends AbstractElementTraceEvent {
        public StandaloneElementStartTraceEvent(final String elementName, final int line, final int col) {
            super(EventType.STANDALONE_ELEMENT_START, elementName, line, col);
        }
    }

    public static final class StandaloneElementEndTraceEvent extends AbstractElementTraceEvent {
        public StandaloneElementEndTraceEvent(final String elementName, final int line, final int col) {
            super(EventType.STANDALONE_ELEMENT_END, elementName, line, col);
        }
    }

    public static final class NonMinimizedStandaloneElementStartTraceEvent extends AbstractElementTraceEvent {
        public NonMinimizedStandaloneElementStartTraceEvent(final String elementName, final int line, final int col) {
            super(EventType.NON_MINIMIZED_STANDALONE_ELEMENT_START, elementName, line, col);
        }
    }

    public static final class NonMinimizedStandaloneElementEndTraceEvent extends AbstractElementTraceEvent {
        public NonMinimizedStandaloneElementEndTraceEvent(final String elementName, final int line, final int col) {
            super(EventType.NON_MINIMIZED_STANDALONE_ELEMENT_END, elementName, line, col);
        }
    }

    public static final class OpenElementStartTraceEvent extends AbstractElementTraceEvent {
        public OpenElementStartTraceEvent(final String elementName, final int line, final int col) {
            super(EventType.OPEN_ELEMENT_START, elementName, line, col);
        }
    }

    public static final class OpenElementEndTraceEvent extends AbstractElementTraceEvent {
        public OpenElementEndTraceEvent(final String elementName, final int line, final int col) {
            super(EventType.OPEN_ELEMENT_END, elementName, line, col);
        }
    }

    public static final class CloseElementStartTraceEvent extends AbstractElementTraceEvent {
        public CloseElementStartTraceEvent(final String elementName, final int line, final int col) {
            super(EventType.CLOSE_ELEMENT_START, elementName, line, col);
        }
    }

    public static final class CloseElementEndTraceEvent extends AbstractElementTraceEvent {
        public CloseElementEndTraceEvent(final String elementName, final int line, final int col) {
            super(EventType.CLOSE_ELEMENT_END, elementName, line, col);
        }
    }

    public static final class AutoCloseElementStartTraceEvent extends AbstractElementTraceEvent {
        public AutoCloseElementStartTraceEvent(final String elementName, final int line, final int col) {
            super(EventType.AUTO_CLOSE_ELEMENT_START, elementName, line, col);
        }
    }

    public static final class AutoCloseElementEndTraceEvent extends AbstractElementTraceEvent {
        public AutoCloseElementEndTraceEvent(final String elementName, final int line, final int col) {
            super(EventType.AUTO_CLOSE_ELEMENT_END, elementName, line, col);
        }
    }

    public static final class UnmatchedCloseElementStartTraceEvent extends AbstractElementTraceEvent {
        public UnmatchedCloseElementStartTraceEvent(final String elementName, final int line, final int col) {
            super(EventType.UNMATCHED_CLOSE_ELEMENT_START, elementName, line, col);
        }
    }

    public static final class UnmatchedCloseElementEndTraceEvent extends AbstractElementTraceEvent {
        public UnmatchedCloseElementEndTraceEvent(final String elementName, final int line, final int col) {
            super(EventType.UNMATCHED_CLOSE_ELEMENT_END, elementName, line, col);
        }
    }

    public static final class AttributeTraceEvent extends MarkupTraceEvent {

        public AttributeTraceEvent(
                final String name,
                final int nameLine, final int nameCol,
                final String operator,
                final int operatorLine, final int operatorCol,
                final String outerValue,
                final int valueLine, final int valueCol) {
            super(EventType.ATTRIBUTE, new int[] {nameLine, operatorLine, valueLine}, new int[] {nameCol, operatorCol, valueCol}, name, operator, outerValue);
            if (name == null || name.trim().equals("")) {
                throw new IllegalArgumentException("Attribute name cannot be null or empty");
            }
        }

        public String getName() {
            return this.contents[0];
        }

        public String getOperator() {
            return this.contents[1];
        }

        public String getOuterValue() {
            return this.contents[2];
        }

        public int getNameLine() {
            return this.lines[0];
        }

        public int getNameCol() {
            return this.cols[0];
        }

        public int getOperatorLine() {
            return this.lines[1];
        }

        public int getOperatorCol() {
            return this.cols[1];
        }

        public int getOuterValueLine() {
            return this.lines[2];
        }

        public int getOuterValueCol() {
            return this.cols[2];
        }

    }

    public static final class XmlDeclarationTraceEvent extends MarkupTraceEvent {

        public XmlDeclarationTraceEvent(
                final String keyword,
                final int keywordLine, final int keywordCol,
                final String version,
                final int versionLine, final int versionCol,
                final String encoding,
                final int encodingLine, final int encodingCol,
                final String standalone,
                final int standaloneLine, final int standaloneCol) {
            super(EventType.XML_DECLARATION,
                  new int[]{keywordLine, versionLine, encodingLine, standaloneLine},
                  new int[]{keywordCol, versionCol, encodingCol, standaloneCol},
                  keyword, version, encoding, standalone);
            if (keyword == null || keyword.trim().equals("")) {
                throw new IllegalArgumentException("Keyword cannot be null or empty");
            }
        }

        public String getKeyword() {
            return this.contents[0];
        }

        public String getVersion() {
            return this.contents[1];
        }

        public String getEncoding() {
            return this.contents[2];
        }

        public String getStandalone() {
            return this.contents[3];
        }

        public int getKeywordLine() {
            return this.lines[0];
        }

        public int getVersionLine() {
            return this.lines[1];
        }

        public int getEncodingLine() {
            return this.lines[2];
        }

        public int getStandaloneLine() {
            return this.lines[3];
        }

        public int getKeywordCol() {
            return this.lines[0];
        }

        public int getVersionCol() {
            return this.lines[1];
        }

        public int getEncodingCol() {
            return this.lines[2];
        }

        public int getStandaloneCol() {
            return this.lines[3];
        }

    }

    public static final class DocTypeTraceEvent extends MarkupTraceEvent {

        public DocTypeTraceEvent(
                final String keyword,
                final int keywordLine, final int keywordCol,
                final String elementName,
                final int elementNameLine, final int elementNameCol,
                final String type,
                final int typeLine, final int typeCol,
                final String publicId,
                final int publicIdLine, final int publicIdCol,
                final String systemId,
                final int systemIdLine, final int systemIdCol,
                final String internalSubset,
                final int internalSubsetLine, final int internalSubsetCol) {
            super(EventType.DOC_TYPE,
                  new int[]{keywordLine, elementNameLine, typeLine, publicIdLine, systemIdLine, internalSubsetLine},
                  new int[]{keywordCol, elementNameCol, typeCol, publicIdCol, systemIdCol, internalSubsetCol},
                  keyword, elementName, type, publicId, systemId, internalSubset);
            if (keyword == null || keyword.trim().equals("")) {
                throw new IllegalArgumentException("Keyword cannot be null or empty");
            }
        }

        public String getKeyword() {
            return this.contents[0];
        }

        public String getElementName() {
            return this.contents[1];
        }

        public String getType() {
            return this.contents[2];
        }

        public String getPublicId() {
            return this.contents[3];
        }

        public String getSystemId() {
            return this.contents[4];
        }

        public String getInternalSubset() {
            return this.contents[5];
        }

        public int getKeywordLine() {
            return this.lines[0];
        }

        public int getElementNameLine() {
            return this.lines[1];
        }

        public int getTypeLine() {
            return this.lines[2];
        }

        public int getPublicIdLine() {
            return this.lines[3];
        }

        public int getSystemIdLine() {
            return this.lines[4];
        }

        public int getInternalSubsetLine() {
            return this.lines[5];
        }

        public int getKeywordCol() {
            return this.lines[0];
        }

        public int getElementNameCol() {
            return this.lines[1];
        }

        public int getTypeCol() {
            return this.lines[2];
        }

        public int getPublicIdCol() {
            return this.lines[3];
        }

        public int getSystemIdCol() {
            return this.lines[4];
        }

        public int getInternalSubsetCol() {
            return this.lines[5];
        }


    }

    public static final class ProcessingInstructionTraceEvent extends MarkupTraceEvent {

        public ProcessingInstructionTraceEvent(
                final String target,
                final int targetLine, final int targetCol,
                final String content,
                final int contentLine, final int contentCol) {
            super(EventType.PROCESSING_INSTRUCTION,
                    new int[]{targetLine, contentLine},
                    new int[]{targetCol, contentCol},
                    target, content);
            if (target == null || target.trim().equals("")) {
                throw new IllegalArgumentException("Target cannot be null or empty");
            }
        }

        public String getTarget() {
            return this.contents[0];
        }

        public String getContent() {
            return this.contents[1];
        }

        public int getTargetLine() {
            return this.lines[0];
        }

        public int getContentLine() {
            return this.lines[1];
        }

        public int getTargetCol() {
            return this.lines[0];
        }

        public int getContentCol() {
            return this.lines[1];
        }

    }



}