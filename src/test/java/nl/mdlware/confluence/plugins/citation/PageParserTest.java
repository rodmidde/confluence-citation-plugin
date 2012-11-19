package nl.mdlware.confluence.plugins.citation;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.net.URISyntaxException;

import static nl.mdlware.confluence.plugins.citation.FileContentAware.readFileAsString;
import static org.junit.Assert.assertEquals;

/**
 * Description for the class PageParserTest:
 * <p/>
 * Example usage:
 * <p/>
 * <pre>
 *
 * </pre>
 *
 * @author mdkr
 * @version Copyright (c) 2012 HAN University, All rights reserved.
 */
public class PageParserTest {
    private PageParser parser;

    @Before
    public void setUp() {
        parser = new PageParser();
    }

    @Test(expected = PageParserException.class)
    public void parseEmptyPage() throws PageParserException {
        parser.parse("");
    }

    @Test
    public void parseFilledPage() throws PageParserException {
        NodeList nodeList = parser.parse("<p><macro name=\"citation\">\n" +
                "        <parameter name=\"referenceDate\">12-12-2012</parameter>\n" +
                "        <parameter name=\"nameOfPage\">ICA</parameter>\n" +
                "        <parameter name=\"author\">Rody Middelkoop</parameter>\n" +
                "        <parameter name=\"nameOfSite\">HAN</parameter>\n" +
                "        <parameter name=\"url\">http://www.han.nl/ica</parameter>\n" +
                "        <parameter name=\"publicationDate\">1-1-1999</parameter>\n" +
                "    </macro></p>");
        assertEquals(1, nodeList.getLength());
    }

    @Test(expected = PageParserException.class)
    public void parsePageWithMultipleXMLRootsButNoCitationsAndNoWrappingP() throws IOException, URISyntaxException, PageParserException {
        assertEquals(0, parser.parse(readFileAsString("bigpage-nocitations-subpages.xml")).getLength());
    }
    @Test
    public void parsePageWithMultipleXMLRootsButNoCitations() throws IOException, URISyntaxException, PageParserException {
        assertEquals(0, parser.parse("<p>" + readFileAsString("bigpage-nocitations-subpages.xml") + "</p>").getLength());
    }
}
