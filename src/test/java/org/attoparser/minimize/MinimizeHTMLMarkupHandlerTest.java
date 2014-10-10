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
package org.attoparser.minimize;

import java.io.StringWriter;
import java.io.Writer;

import junit.framework.TestCase;
import org.attoparser.IMarkupParser;
import org.attoparser.MarkupParser;
import org.attoparser.config.ParseConfiguration;
import org.attoparser.minimize.MinimizeHTMLMarkupHandler.MinimizeMode;
import org.attoparser.output.OutputMarkupHandler;

import static org.attoparser.minimize.MinimizeHTMLMarkupHandler.MinimizeMode.COMPLETE;
import static org.attoparser.minimize.MinimizeHTMLMarkupHandler.MinimizeMode.ONLY_WHITE_SPACE;

/**
 * 
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 2.0.0
 *
 */
public class MinimizeHTMLMarkupHandlerTest extends TestCase {


    public void test() throws Exception {

        final ParseConfiguration xmlConfig = ParseConfiguration.xmlConfiguration();
        final ParseConfiguration xmlAutoCloseConfig = ParseConfiguration.xmlConfiguration();
        xmlAutoCloseConfig.setElementBalancing(ParseConfiguration.ElementBalancing.AUTO_CLOSE);
        final ParseConfiguration htmlConfig = ParseConfiguration.htmlConfiguration();


        check(htmlConfig, ONLY_WHITE_SPACE, "<div >hello</div >", "<div>hello</div>");
        check(htmlConfig, ONLY_WHITE_SPACE, "<hr /><div >hello</div >", "<hr/><div>hello</div>");
        check(htmlConfig, ONLY_WHITE_SPACE, "<div   class=\"one\"  \n   id=\"two\">hello</div>", "<div class=\"one\" id=\"two\">hello</div>");
        check(htmlConfig, ONLY_WHITE_SPACE, "<div   class=\"one\"  \n   id=\"two\"  >hello</div>", "<div class=\"one\" id=\"two\">hello</div>");
        check(htmlConfig, ONLY_WHITE_SPACE, "<div   class=\"one two\"  \n   id=\"two\"  >hello</div>", "<div class=\"one two\" id=\"two\">hello</div>");
        check(htmlConfig, ONLY_WHITE_SPACE, "<div   class='one two'  \n   id='two'  >hello</div>", "<div class='one two' id='two'>hello</div>");
        check(htmlConfig, ONLY_WHITE_SPACE, "<div   class =   \"one\"  \n   id =\"two\"  >hello</div>", "<div class=\"one\" id=\"two\">hello</div>");
        check(htmlConfig, ONLY_WHITE_SPACE, "<div   class =   \"one,two\"  \n   id =\"two\"  >hello</div>", "<div class=\"one,two\" id=\"two\">hello</div>");
        check(htmlConfig, ONLY_WHITE_SPACE, "<div   class =   \"\" >", "<div class=\"\">");
        check(htmlConfig, ONLY_WHITE_SPACE, "<div   class =   >", "<div class=>");
        check(htmlConfig, ONLY_WHITE_SPACE, "<div   class=>", "<div class=>");
        check(htmlConfig, ONLY_WHITE_SPACE, "<div   class =   something >", "<div class=something>");
        check(htmlConfig, ONLY_WHITE_SPACE, "<!-- something -->hey", "<!-- something -->hey");
        check(htmlConfig, ONLY_WHITE_SPACE, "<!-- something --> hey", "<!-- something --> hey");
        check(htmlConfig, ONLY_WHITE_SPACE, "<!-- something --> hey    ja", "<!-- something --> hey ja");
        check(htmlConfig, ONLY_WHITE_SPACE, "<!-- something --> hey  \n ja", "<!-- something --> hey ja");
        check(htmlConfig, ONLY_WHITE_SPACE, " <!-- something --> hey", " <!-- something --> hey");
        check(htmlConfig, ONLY_WHITE_SPACE, " <!-- something --> hey    ja", " <!-- something --> hey ja");
        check(htmlConfig, ONLY_WHITE_SPACE, "\n <!-- something --> hey  \n  ja", " <!-- something --> hey ja");

        check(htmlConfig, COMPLETE, "<div >hello</div >", "<div>hello</div>");
        check(htmlConfig, COMPLETE, "<hr /><div >hello</div >", "<hr/><div>hello</div>");
        check(htmlConfig, COMPLETE, "<div   class=\"one\"  \n   id=\"two\">hello</div>", "<div class=one id=two>hello</div>");
        check(htmlConfig, COMPLETE, "<div   class=\"one\"  \n   id=\"two\"  >hello</div>", "<div class=one id=two>hello</div>");
        check(htmlConfig, COMPLETE, "<div   class=\"one two\"  \n   id=\"two\"  >hello</div>", "<div class=\"one two\" id=two>hello</div>");
        check(htmlConfig, COMPLETE, "<div   class='one two'  \n   id='two'  >hello</div>", "<div class='one two' id=two>hello</div>");
        check(htmlConfig, COMPLETE, "<div   class =   \"one\"  \n   id =\"two\"  >hello</div>", "<div class=one id=two>hello</div>");
        check(htmlConfig, COMPLETE, "<div   class =   \"one,two\"  \n   id =\"two\"  >hello</div>", "<div class=\"one,two\" id=two>hello</div>");
        check(htmlConfig, COMPLETE, "<div   class =   \"\" >", "<div class=\"\">");
        check(htmlConfig, COMPLETE, "<div   class =   >", "<div class=>");
        check(htmlConfig, COMPLETE, "<div   class=>", "<div class=>");
        check(htmlConfig, COMPLETE, "<div   class =   something >", "<div class=something>");
        check(htmlConfig, COMPLETE, "<!-- something -->hey", "hey");
        check(htmlConfig, COMPLETE, "<!-- something --> hey", " hey");
        check(htmlConfig, COMPLETE, "<!-- something --> hey    ja", " hey ja");
        check(htmlConfig, COMPLETE, "<!-- something --> hey  \n ja", " hey ja");
        check(htmlConfig, COMPLETE, " <!-- something --> hey", " hey");
        check(htmlConfig, COMPLETE, " <!-- something --> hey    ja", " hey ja");
        check(htmlConfig, COMPLETE, "\n <!-- something --> hey  \n  ja", " hey ja");

    }


    private static void check(final ParseConfiguration configuration, final MinimizeMode minimizeMode, final String input, final String expectedOutput) throws Exception {

        final Writer writer = new StringWriter();

        final IMarkupParser parser = new MarkupParser(configuration);
        final MinimizeHTMLMarkupHandler handler = new MinimizeHTMLMarkupHandler(minimizeMode, new OutputMarkupHandler(writer));

        parser.parse(input, handler);

        final String output = writer.toString();

        assertEquals(expectedOutput, output);

    }



}
