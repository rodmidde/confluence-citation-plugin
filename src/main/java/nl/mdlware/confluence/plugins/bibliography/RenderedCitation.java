package nl.mdlware.confluence.plugins.bibliography;

import static nl.mdlware.confluence.plugins.bibliography.Validator.isSet;

/**
 * Description for the class RenderedCitation:
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
public class RenderedCitation {
    public RenderedCitation(Citation citation) {
        this.citation = citation;
    }

    public String render() {
        return shorten() + "-" + getYear();
    }

    private String getYear() {
        if (isSet(citation.getPublicationDate())) return split(citation.getPublicationDate());
        if (isSet(citation.getReferenceDate())) return split(citation.getReferenceDate());
        return null;
    }

    private String split(String date) {
        return date.split("-")[2];
    }

    private String shorten() {
        if (isSet(citation.getAuthor())) return shorten(citation.getAuthor());
        if (isSet(citation.getNameOfSite())) return shorten(citation.getNameOfSite());
        return null;
    }

    private String shorten(String identifierToShorten) {
        String[] identifierToShortenParts = identifierToShorten.split(" ");
        String identifierToShortenPart = identifierToShortenParts[identifierToShortenParts.length - 1];
        return identifierToShortenPart.substring(0, identifierToShortenPart.length() >= SHORTVAL ? SHORTVAL : identifierToShortenPart.length()).toUpperCase();
    }

    private static final int SHORTVAL = 3;
    private Citation citation;
}
