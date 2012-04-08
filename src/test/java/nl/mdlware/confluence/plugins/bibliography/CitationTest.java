package nl.mdlware.confluence.plugins.bibliography;

import nl.mdlware.confluence.plugins.citation.Citation;
import org.junit.Test;

/**
 * Description for the class CitationTest:
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
public class CitationTest {
    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithEmptyURL() {
        new Citation("", "author", "somedate", "somedate", "page", "site");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithNullURL() {
        new Citation(null, "author", "somedate", "somedate", "page", "site");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithNullReferenceDate() {
        new Citation("url", "author", null, "", "page", "site");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithEmptyReferenceDate() {
        new Citation("url", "author", "", "", "page", "site");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithInvalidReferenceDate() {
        new Citation("url", "author", "1234", "", "page", "site");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithInvalidPublicationDate() {
        new Citation("url", "author", "1-1-2000", "1234", "page", "site");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithEmptyPagename() {
        new Citation("url", "author", "somedate", "somedate", "", "site");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithNullPagename() {
        new Citation("url", "author", "somedate", "somedate", null, "site");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithEmptySitename() {
        new Citation("url", "author", "somedate", "somedate", "page", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithNullSitename() {
        new Citation("url", "author", "somedate", "somedate", "page", null);
    }

}
