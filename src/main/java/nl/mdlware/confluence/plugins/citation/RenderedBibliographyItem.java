package nl.mdlware.confluence.plugins.citation;

import com.atlassian.sal.api.message.I18nResolver;

import java.io.StringWriter;

import static nl.mdlware.confluence.plugins.citation.Validator.isSet;

/**
 * @author Rody Middelkoop
 */
class RenderedBibliographyItem  {
    private final Citation citation;
    private final I18nResolver i18n;

    public RenderedBibliographyItem(Citation citation, I18nResolver i18n) {
        this.citation = citation;
        this.i18n = i18n;
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
        stringWriter.append(getI18NText("retrieved") + " " + citation.getReferenceDate() + ", ");
        stringWriter.append(getI18NText("from") + " "  + citation.getNameOfSite() + ": ");
        stringWriter.append("<a href='" + citation.getUrl() + "'>" + citation.getUrl() + "</a>");

        return stringWriter.toString().trim();
    }

    private String getI18NText(String propertyName) {
        return i18n.getText("nl.mdlware.confluence.plugins.citation.confluence-citation-plugin." + propertyName);
    }
}
