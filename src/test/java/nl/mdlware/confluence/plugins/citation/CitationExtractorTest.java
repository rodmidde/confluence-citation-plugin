package nl.mdlware.confluence.plugins.citation;

import org.junit.Test;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.List;

import static nl.mdlware.confluence.plugins.citation.FileContentAware.readFileAsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Description for the class CitationExtractorTest:
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
public class CitationExtractorTest {
    private XMLDocumentWrapper xmlDocumentWrapper = new XMLDocumentWrapper();

    @Test
    public void testExtractFromEmptyPage() {
        CitationExtractor citationExtractor = new CitationExtractor("");
        assertEquals(0, citationExtractor.extract().size());
    }

    @Test
    public void testExtractFromPageWithNoCitations() {
        CitationExtractor citationExtractor = new CitationExtractor("<p>Test</p>");
        assertEquals(0, citationExtractor.extract().size());
    }

    @Test
    public void testExtractFromPageWithOneCitation() {
        CitationExtractor citationExtractor = new CitationExtractor(readFileAsString("one-citation.xml"));
        List<Citation> citationList = citationExtractor.extract();
        assertEquals(1, citationList.size());
        assertEquals("HAN", citationList.get(0).getNameOfSite());
        assertEquals("ICA", citationList.get(0).getNameOfPage());
        assertEquals("Rody Middelkoop", citationList.get(0).getAuthor());
    }

    @Test
    public void testExtractFromPageWithTwoCitations() {
        CitationExtractor citationExtractor = new CitationExtractor(readFileAsString("two-citations.xml"));
        List<Citation> citationList = citationExtractor.extract();
        assertEquals(2, citationList.size());
        assertEquals("DDOA", citationList.get(1).getNameOfSite());
        assertEquals("DDOA", citationList.get(1).getNameOfPage());
        assertEquals("Rody Middelkoop", citationList.get(1).getAuthor());
    }

    @Test
    public void testExtractFromPageWithTwoCitationsAndAnotherMacro() {
        CitationExtractor citationExtractor = new CitationExtractor(readFileAsString("two-citations-and-another-macro.xml"));
        List<Citation> citationList = citationExtractor.extract();
        assertEquals(2, citationList.size());
        assertEquals("DDOA", citationList.get(1).getNameOfSite());
        assertEquals("DDOA", citationList.get(1).getNameOfPage());
        assertEquals("Rody Middelkoop", citationList.get(1).getAuthor());
    }

    @Test
    public void testInvalidPageContentsLeadToParseException() throws PageParserException {
        String inputToParse = "INVALID";
        inputToParse = xmlDocumentWrapper.wrapIntoValidXML(inputToParse);
        inputToParse = inputToParse.replaceAll("ac:", "");

        PageParser pageParser = mock(PageParser.class);
        when(pageParser.parse(inputToParse)).thenThrow(new PageParserException("Invalid input"));
        CitationExtractor citationExtractor = new CitationExtractor("INVALID");
        citationExtractor.setPageParser(pageParser);
        assertEquals(0, citationExtractor.extract().size());
    }

    @Test
    public void testInvalidPageContentsLeadToXPathException() throws PageParserException, XPathExpressionException {
        String inputToParse = "INVALID";
        inputToParse = xmlDocumentWrapper.wrapIntoValidXML(inputToParse);
        inputToParse = inputToParse.replaceAll("ac:", "");

        NodeList nodeList = mock(NodeList.class);
        when(nodeList.getLength()).thenReturn(1);
        when(nodeList.item(0)).thenReturn(null);

        PageParser pageParser = mock(PageParser.class);
        when(pageParser.parse(inputToParse)).thenReturn(nodeList);

        XPathFactory factory = mock(XPathFactory.class);
        XPath path = mock(XPath.class);
        when(path.evaluate("parameter", null, XPathConstants.NODESET)).thenThrow(new XPathExpressionException("Invalid Xpath Expression"));
        when(factory.newXPath()).thenReturn(path);

        CitationExtractor citationExtractor = new CitationExtractor("INVALID");
        citationExtractor.setxPathFactory(factory);
        citationExtractor.setPageParser(pageParser);

        assertEquals(0, citationExtractor.extract().size());
    }

}
