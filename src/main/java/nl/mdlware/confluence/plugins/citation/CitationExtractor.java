package nl.mdlware.confluence.plugins.citation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nl.mdlware.confluence.plugins.citation.Validator.isSet;

/**
 * @author mdkr
 * @version Copyright (c) 2012 HAN University, All rights reserved.
 */
public class CitationExtractor {
    private XMLDocumentWrapper xmlDocumentWrapper = new XMLDocumentWrapper();

    public List<Citation> extract() {
        List<Citation> citations = new ArrayList<Citation>();
        if (isSet(pageContents)) {
            parsePageContentsToCitations(citations);
        }
        return citations;
    }

    private void parsePageContentsToCitations(List<Citation> citations) {
        NodeList matchedNodes = null;
        try {
            matchedNodes = pageParser.parse(pageContents);
            for (int i = 0; i < matchedNodes.getLength(); i++) {
                Map mapFromNode = createMapFromNode(matchedNodes.item(i));
                citations.add(new CitationFactory().createCitationFromMap(mapFromNode));
            }
        } catch (PageParserException e) {
            LOG.error(e.getMessage());
            throw new CitationExtractionException(e);
        } catch (XPathExpressionException e) {
            LOG.error(e.getMessage());
            throw new CitationExtractionException(e);
        }
    }

    private Map createMapFromNode(Node matchedNode) throws XPathExpressionException {
        Map params = new HashMap();

        NodeList parameters = selectNodes(matchedNode, "parameter");
        if (parameters != null) {
            for (int i = 0; i < parameters.getLength(); i++) {
                Node item = parameters.item(i);
                params.put(item.getAttributes().getNamedItem("name").getTextContent(), item.getTextContent());
            }
            return params;
        }
        throw new XPathExpressionException("Cannot parse XPath expression");
    }

    private NodeList selectNodes(Node matchedNode, String parameter) throws XPathExpressionException {
        XPath xPath = xPathFactory.newXPath();
        return (NodeList) xPath.evaluate(parameter, matchedNode, XPathConstants.NODESET);
    }

    private String makeParseable(String pageContents) {
        if (isSet(pageContents)) {
            pageContents = xmlDocumentWrapper.wrapIntoValidXML(pageContents).replaceAll("ac:", "");
        }
        return pageContents;
    }

    public CitationExtractor(String pageContents) {
        this.pageContents = makeParseable(pageContents);
        setPageParser(new PageParser());
        setxPathFactory(XPathFactory.newInstance());
    }

    public void setPageParser(PageParser pageParser) {
        this.pageParser = pageParser;
    }

    public void setxPathFactory(XPathFactory xPathFactory) {
        this.xPathFactory = xPathFactory;
    }

    private PageParser pageParser;
    private XPathFactory xPathFactory;
    private String pageContents;

    private static final Logger LOG = LoggerFactory.getLogger(CitationMacro.class);
}
