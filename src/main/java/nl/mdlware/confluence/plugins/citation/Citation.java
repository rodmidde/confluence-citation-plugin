package nl.mdlware.confluence.plugins.citation;

import static nl.mdlware.confluence.plugins.citation.Validator.isSet;

/**
 * Description for the class Citation:
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
public class Citation implements Comparable<Citation>{
    public Citation(String url, String author, String referenceDate, String publicationDate, String nameOfPage, String nameOfSite) {
        this.url = url;
        this.author = author;
        this.referenceDate = referenceDate;
        this.publicationDate = publicationDate;
        this.nameOfPage = nameOfPage;
        this.nameOfSite = nameOfSite;
        validate();
    }

    private void validate() {
        if (!Validator.isSet(url)) throw new IllegalArgumentException("Empty URL");
        if (!Validator.isSet(nameOfPage)) throw new IllegalArgumentException("Empty Pagename");
        if (!Validator.isSet(nameOfSite)) throw new IllegalArgumentException("Empty Sitename");
        if (!Validator.isSet(referenceDate)) throw new IllegalArgumentException("Empty Referencedate");
        if (!DateValidator.isValidDate(referenceDate)) throw new IllegalArgumentException("Invalid Referencedate");
        if (this.publicationDate != null && !DateValidator.isValidDate(this.publicationDate))
            throw new IllegalArgumentException("Invalid Publicationdate");
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

    // required
    private final String url;
    private final String nameOfPage;
    private final String nameOfSite;
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
