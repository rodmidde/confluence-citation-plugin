package nl.mdlware.confluence.plugins.citation;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

/**
 * @author Rody Middelkoop
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
            return (NodeList) xPath.evaluate("//structured-macro[@name='citation']", document, XPathConstants.NODESET);
        } catch (Exception e) {
            throw new PageParserException(e);
        }
    }
}
