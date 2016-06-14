package nl.mdlware.confluence.plugins.citation;

import com.atlassian.sal.api.message.I18nResolver;

/**
 * @author Rody Middelkoop
 */

public class Citation implements Comparable<Citation> {

    public Citation(String url, String author, String referenceDate, String publicationDate, String nameOfPage, String nameOfSite, String bibliographyPage, I18nResolver i18n) {
        this.url = url;
        this.author = author;
        this.referenceDate = referenceDate;
        this.publicationDate = publicationDate;
        this.nameOfPage = nameOfPage;
        this.nameOfSite = nameOfSite;
        this.bibliographyPage = bibliographyPage;
        this.i18n = i18n;
        validate();
    }

    private void validate() {
        validate(url, "emptyurl");
        validate(nameOfPage, "emptypage");
        validate(nameOfSite, "emptysite");
        validate(referenceDate, "emptyrefdate");
        validate(bibliographyPage, "emptybibpage");

        if (this.publicationDate != null && !DateValidator.isValidDate(this.publicationDate)) {
            throw new IllegalArgumentException(getI18NText("invalidpubdate"));
        }
    }

    private void validate(String field, String exceptionMessageKey) {
        if (!Validator.isSet(field)) {
            throw new IllegalArgumentException(getI18NText(exceptionMessageKey));
        }
    }

    private String getI18NText(String propertyName) {
        return i18n.getText("nl.mdlware.confluence.plugins.citation.confluence-citation-plugin." + propertyName);
    }

    public String getNameOfSite() {
        return nameOfSite;
    }

    public String getAuthor() {
        return author;
    }

    public String getReferenceDate() {
        return referenceDate;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getUrl() {
        return url;
    }

    public String getNameOfPage() {
        return nameOfPage;
    }

    public String getBibliographyPage() {
        return bibliographyPage;
    }
    // required
    private final String url;
    private final String nameOfPage;

    private final String nameOfSite;

    private final String bibliographyPage;
    private I18nResolver i18n;
    private final String referenceDate;

    //optional
    private final String author;
    private final String publicationDate;

    public int compareTo(Citation citation) {
        RenderedCitation renderedCitation = new RenderedCitation(this);
        RenderedCitation otherRenderedCitation = new RenderedCitation(citation);
        return renderedCitation.render().compareTo(otherRenderedCitation.render());
    }
}
