package nl.mdlware.confluence.plugins.citation;

import java.io.StringWriter;

import static nl.mdlware.confluence.plugins.citation.Validator.isSet;

/**
 * Description for the class RenderedBibliographyItem:
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
public class RenderedBibliographyItem  {
    private Citation citation;

    public RenderedBibliographyItem(Citation citation) {
        this.citation = citation;
    }

    public String render() {
        StringWriter stringWriter = new StringWriter();
        if (isSet(citation.getAuthor())) {
            stringWriter.append(citation.getAuthor());
        }
        if (isSet(citation.getPublicationDate()))
        {
            stringWriter.append(" (" + citation.getPublicationDate() + "). ");
        }
        if (isSet(citation.getNameOfPage()))
        {
            stringWriter.append(citation.getNameOfPage() + ". ");
        }
        stringWriter.append("Retrieved " + citation.getReferenceDate() + ", ");
        stringWriter.append("from " + citation.getNameOfSite() + ": ");
        stringWriter.append("<a href='" + citation.getUrl() + "'>" + citation.getUrl() + "</a>");
        return stringWriter.toString();
    }
}
