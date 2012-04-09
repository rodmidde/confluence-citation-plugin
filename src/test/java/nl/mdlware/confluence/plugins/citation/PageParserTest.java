package nl.mdlware.confluence.plugins.citation;

import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.junit.Test;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXParseException;

import java.util.List;

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
    @Test(expected = PageParserException.class)
    public void parseEmptyPage() throws PageParserException
    {
        PageParser parser = new PageParser();
        parser.parse("");
    }

    @Test
    public void parseFilledPage() throws PageParserException
    {
        PageParser parser = new PageParser();
        NodeList nodeList = parser.parse("<macro name=\"citation\">\n" +
                "        <parameter name=\"referenceDate\">12-12-2012</parameter>\n" +
                "        <parameter name=\"nameOfPage\">ICA</parameter>\n" +
                "        <parameter name=\"author\">Rody Middelkoop</parameter>\n" +
                "        <parameter name=\"nameOfSite\">HAN</parameter>\n" +
                "        <parameter name=\"url\">http://www.han.nl/ica</parameter>\n" +
                "        <parameter name=\"publicationDate\">1-1-1999</parameter>\n" +
                "    </macro>");
        assertEquals(1, nodeList.getLength());
    }
}
