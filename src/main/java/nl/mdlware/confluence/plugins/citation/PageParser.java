package nl.mdlware.confluence.plugins.citation;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * Description for the class PageParser:
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
public class PageParser {

    public NodeList parse(String pageContents) throws PageParserException {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = null;
            documentBuilder = documentBuilderFactory.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(pageContents));
            Document document = documentBuilder.parse(is);
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            return (NodeList)xPath.evaluate("//macro[@name='citation']", document, XPathConstants.NODESET);
        } catch (SAXParseException e) {
            throw new PageParserException(e);
        } catch (ParserConfigurationException e) {
            throw new PageParserException(e);
        } catch (SAXException e) {
            throw new PageParserException(e);
        } catch (XPathExpressionException e) {
            throw new PageParserException(e);
        } catch (IOException e) {
            throw new PageParserException(e);
        }
    }
}
