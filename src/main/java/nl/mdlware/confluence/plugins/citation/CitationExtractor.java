package nl.mdlware.confluence.plugins.citation;

import com.atlassian.confluence.pages.Page;
import com.atlassian.sal.api.message.I18nResolver;
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
 */
public class CitationExtractor {
    public List<Citation> extract() {
        List<Citation> citations = new ArrayList<Citation>();
        parsePageContentsToCitationsForTheCurrentPage(citations);
        return citations;
    }

    private void parsePageContentsToCitationsForTheCurrentPage(List<Citation> citations) {
        NodeList matchedNodes = null;
        try {
            matchedNodes = pageParser.parse(pageContents);
            for (int i = 0; i < matchedNodes.getLength(); i++) {
                Map mapFromNode = createMapFromNode(matchedNodes.item(i));
                Citation citation = new CitationFactory().createCitationFromMap(mapFromNode, i18n);
                if (citation.getBibliographyPage().equals(this.pageTitle))
                    citations.add(citation);
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
                params.put(getTextContent(item.getAttributes().getNamedItem("name")), getTextContent(item));
            }
            return params;
        }
        throw new XPathExpressionException("Cannot parse XPath expression");
    }

    private String getTextContent(Node item) {
        String textContent = item.getTextContent();
        if (!textContent.isEmpty()) return textContent;
        else return item.getFirstChild().getFirstChild().getAttributes().item(0).getTextContent();
    }

    private NodeList selectNodes(Node matchedNode, String parameter) throws XPathExpressionException {
        XPath xPath = xPathFactory.newXPath();
        return (NodeList) xPath.evaluate(parameter, matchedNode, XPathConstants.NODESET);
    }

    private String makeParseable(String pageContents) {
        return xmlDocumentWrapper.wrapIntoValidXML(pageContents).replaceAll("ac:", "");
    }

    public CitationExtractor(Page page, String titleOfBibliographyPage, I18nResolver i18n) {
        this.pageContents = makeParseable(page.getBodyAsString());
        this.pageTitle = titleOfBibliographyPage;
        this.i18n = i18n;
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
    private XMLDocumentWrapper xmlDocumentWrapper = new XMLDocumentWrapper();
    private XPathFactory xPathFactory;

    private final String pageContents;
    private final String pageTitle;
    private final I18nResolver i18n;

    private static final Logger LOG = LoggerFactory.getLogger(CitationMacro.class);
}
