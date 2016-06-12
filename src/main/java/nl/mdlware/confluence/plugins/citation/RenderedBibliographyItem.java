package nl.mdlware.confluence.plugins.citation;

import java.io.StringWriter;

import static nl.mdlware.confluence.plugins.citation.Validator.isSet;

/**
 * @author Rody Middelkoop
 */
class RenderedBibliographyItem  {
    private final Citation citation;

    public RenderedBibliographyItem(Citation citation) {
        this.citation = citation;
    }

    public String render() {
        StringWriter stringWriter = new StringWriter();
        if (isSet(citation.getAuthor())) {
            stringWriter.append(citation.getAuthor() + ". ");
        }
        if (isSet(citation.getPublicationDate()))
        {
            stringWriter.append(" (" + citation.getPublicationDate() + "). ");
        }
        if (isSet(citation.getNameOfPage()))
        {
            stringWriter.append(citation.getNameOfPage()).append(". ");
        }
        stringWriter.append(citation.getI18NText("retrieved") + " " + citation.getReferenceDate() + ", ");
        stringWriter.append(citation.getI18NText("from") + " "  + citation.getNameOfSite() + ": ");
        stringWriter.append("<a href='" + citation.getUrl() + "'>" + citation.getUrl() + "</a>");

        return stringWriter.toString().trim();
    }
}
