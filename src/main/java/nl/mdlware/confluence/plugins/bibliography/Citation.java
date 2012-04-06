package nl.mdlware.confluence.plugins.bibliography;

import static nl.mdlware.confluence.plugins.bibliography.DateValidator.*;
import static nl.mdlware.confluence.plugins.bibliography.Validator.*;

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
public class Citation {
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
        if (!isSet(url)) throw new IllegalArgumentException("Empty URL");
        if (!isSet(nameOfPage)) throw new IllegalArgumentException("Empty Pagename");
        if (!isSet(nameOfSite)) throw new IllegalArgumentException("Empty Sitename");
        if (!isSet(referenceDate)) throw new IllegalArgumentException("Empty Referencedate");
        if (!isValidDate(referenceDate)) throw new IllegalArgumentException("Invalid Referencedate");
        if (this.publicationDate != null && !isValidDate(this.publicationDate))
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
    
    public String getPublicationDate()
    {
        return publicationDate;
    }

    // required
    private String url;
    private String nameOfPage;
    private String nameOfSite;
    private String referenceDate;

    //optional
    private String author;
    private String publicationDate;
}
