package nl.mdlware.confluence.plugins.citation;

import com.atlassian.confluence.pages.Page;
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
    private TestI18NResolver i18n = new TestI18NResolver();

    @Test
    public void testExtractFromEmptyPage() {
        Page page = mock(Page.class);
        when(page.getBodyAsString()).thenReturn("");

        CitationExtractor citationExtractor = new CitationExtractor(page, page.getTitle(), i18n);
        assertEquals(0, citationExtractor.extract().size());
    }

    @Test
    public void testExtractFromPageWithNullBody() {
        Page page = mock(Page.class);
        when(page.getBodyAsString()).thenReturn(null);

        CitationExtractor citationExtractor = new CitationExtractor(page, page.getTitle(), i18n);
        assertEquals(0, citationExtractor.extract().size());
    }

    @Test
    public void testExtractFromPageWithNoCitations() {
        Page page = mock(Page.class);
        when(page.getBodyAsString()).thenReturn("<p>Test</p>");

        CitationExtractor citationExtractor = new CitationExtractor(page, page.getTitle(), i18n);
        assertEquals(0, citationExtractor.extract().size());
    }

    @Test
    public void testExtractFromPageWithOneCitation() {
        Page page = mock(Page.class);
        when(page.getBodyAsString()).thenReturn(readFileAsString("one-citation.xml"));
        when(page.getTitle()).thenReturn("Bibliography");

        CitationExtractor citationExtractor = new CitationExtractor(page, page.getTitle(), i18n);
        List<Citation> citationList = citationExtractor.extract();
        assertEquals(1, citationList.size());
        assertEquals("HAN", citationList.get(0).getNameOfSite());
        assertEquals("ICA", citationList.get(0).getNameOfPage());
        assertEquals("Rody Middelkoop", citationList.get(0).getAuthor());
    }

    @Test
    public void testExtractFromPageWithTwoCitations() {
        Page page = mock(Page.class);
        when(page.getBodyAsString()).thenReturn(readFileAsString("two-citations.xml"));
        when(page.getTitle()).thenReturn("Bibliography");

        CitationExtractor citationExtractor = new CitationExtractor(page, page.getTitle(), i18n);
        List<Citation> citationList = citationExtractor.extract();
        assertEquals(2, citationList.size());
        assertEquals("DDOA", citationList.get(1).getNameOfSite());
        assertEquals("DDOA", citationList.get(1).getNameOfPage());
        assertEquals("Rody Middelkoop", citationList.get(1).getAuthor());
    }

    @Test
    public void testExtractFromPageWithTwoCitationsAndAnotherMacro() {
        Page page = mock(Page.class);
        when(page.getBodyAsString()).thenReturn(readFileAsString("two-citations-and-another-macro.xml"));
        when(page.getTitle()).thenReturn("Bibliography");

        CitationExtractor citationExtractor = new CitationExtractor(page, page.getTitle(), i18n);
        List<Citation> citationList = citationExtractor.extract();
        assertEquals(2, citationList.size());
        assertEquals("DDOA", citationList.get(1).getNameOfSite());
        assertEquals("DDOA", citationList.get(1).getNameOfPage());
        assertEquals("Rody Middelkoop", citationList.get(1).getAuthor());
    }

    @Test
    public void testExtractFromPageWithTwoCitationsAndAnotherMacroButNotEqualToCurrentBibPage() {
        Page page = mock(Page.class);
        when(page.getBodyAsString()).thenReturn(readFileAsString("two-citations-and-another-macro.xml"));
        when(page.getTitle()).thenReturn("Bieb");

        CitationExtractor citationExtractor = new CitationExtractor(page, page.getTitle(), i18n);
        List<Citation> citationList = citationExtractor.extract();
        assertEquals(0, citationList.size());
    }

    @Test(expected = CitationExtractionException.class)
    public void testInvalidPageContentsLeadToParseException() throws PageParserException {
        String inputToParse = "INVALID";
        inputToParse = xmlDocumentWrapper.wrapIntoValidXML(inputToParse);
        inputToParse = inputToParse.replaceAll("ac:", "");

        PageParser pageParser = mock(PageParser.class);
        when(pageParser.parse(inputToParse)).thenThrow(new PageParserException("Invalid input"));

        Page page = mock(Page.class);
        when(page.getBodyAsString()).thenReturn("INVALID");
        when(page.getTitle()).thenReturn("Bibliography");

        CitationExtractor citationExtractor = new CitationExtractor(page, page.getTitle(), i18n);
        citationExtractor.setPageParser(pageParser);
        citationExtractor.extract();
    }

    @Test(expected = CitationExtractionException.class)
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

        Page page = mock(Page.class);
        when(page.getBodyAsString()).thenReturn("INVALID");
        when(page.getTitle()).thenReturn("Bibliography");

        CitationExtractor citationExtractor = new CitationExtractor(page, page.getTitle(), i18n);
        citationExtractor.setxPathFactory(factory);
        citationExtractor.setPageParser(pageParser);

        citationExtractor.extract();
    }

}
