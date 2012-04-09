package nl.mdlware.confluence.plugins.citation;

import org.dom4j.DocumentException;
import org.junit.Test;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.List;

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
        inputToParse = "<?xml version=\"1.0\"?><!DOCTYPE some_name [<!ENTITY nbsp \"&#160;\">]>" + inputToParse;
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
        inputToParse = "<?xml version=\"1.0\"?><!DOCTYPE some_name [<!ENTITY nbsp \"&#160;\">]>" + inputToParse;
        inputToParse = inputToParse.replaceAll("ac:", "");

        NodeList nodeList = mock(NodeList.class);
        when(nodeList.getLength()).thenReturn(1);
        when(nodeList.item(0)).thenReturn(null);

        PageParser pageParser = mock(PageParser.class);
        when(pageParser.parse("<?xml version=\"1.0\"?><!DOCTYPE some_name [<!ENTITY nbsp \"&#160;\">]>INVALID")) .thenReturn(nodeList);
                
        XPathFactory factory = mock(XPathFactory.class);
        XPath path = mock(XPath.class);
        when(path.evaluate("parameter",null, XPathConstants.NODESET)).thenThrow(new XPathExpressionException("Invalid Xpath Expression"));
        when(factory.newXPath()).thenReturn(path);

        CitationExtractor citationExtractor = new CitationExtractor("INVALID");
        citationExtractor.setxPathFactory(factory);
        citationExtractor.setPageParser(pageParser);

        assertEquals(0, citationExtractor.extract().size());
    }
    /**
     * @see  http://snippets.dzone.com/posts/show/4480
     *
     * @param filePath The name of the file as it resides in your classpath
     * @return The contents of the file as a String
     * @throws java.io.IOException
     */
    private String readFileAsString(String filePath)  {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(this
                .getClass().getClassLoader().getResourceAsStream(filePath)));
        char[] buf = new char[1024];
        int numRead = 0;
        try {
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileData.toString();
    }
}
