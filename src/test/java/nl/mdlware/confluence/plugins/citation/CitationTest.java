package nl.mdlware.confluence.plugins.citation;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    private TestI18NResolver testI18NResolver = new TestI18NResolver();

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithEmptyURL() {
        new Citation("", "author", "somedate", "somedate", "page", "site", "Bibliography", testI18NResolver);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithNullURL() {
        new Citation(null, "author", "somedate", "somedate", "page", "site", "Bibliography", testI18NResolver);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithNullReferenceDate() {
        new Citation("url", "author", null, "", "page", "site", "Bibliography", testI18NResolver);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithEmptyReferenceDate() {
        new Citation("url", "author", "", "", "page", "site", "Bibliography", testI18NResolver);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithInvalidReferenceDate() {
        new Citation("url", "author", "1234", "", "page", "site", "Bibliography", testI18NResolver);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithInvalidPublicationDate() {
        new Citation("url", "author", "1-1-2000", "1234", "page", "site", "Bibliography", testI18NResolver);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithEmptyPagename() {
        new Citation("url", "author", "somedate", "somedate", "", "site", "Bibliography", testI18NResolver);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithNullPagename() {
        new Citation("url", "author", "somedate", "somedate", null, "site", "Bibliography", testI18NResolver);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithEmptySitename() {
        new Citation("url", "author", "somedate", "somedate", "page", "", "Bibliography", testI18NResolver);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithNullSitename() {
        new Citation("url", "author", "somedate", "somedate", "page", null, "Bibliography", testI18NResolver);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithEmptyBibPage() {
        new Citation("url", "author", "somedate", "somedate", "page", "site", "", testI18NResolver);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCitationWithNullBibPage() {
        new Citation("url", "author", "somedate", "somedate", "page", "site", null, testI18NResolver);
    }

    @Test
    public void testCompareTo()
    {
        Citation citation = new Citation("http://www.han.nl","Rody Middelkoop", "12-12-2000", "1-1-1999", "ICA", "HAN", "Bibliography", testI18NResolver);
        Citation copyCitation = new Citation("http://www.han.nl","Rody Middelkoop", "12-12-2000", "1-1-1999", "ICA", "HAN", "Bibliography", testI18NResolver);
        Citation citationWithoutAuthor = new Citation("http://www.han.nl","", "12-12-2000", "1-1-1999", "ICA", "HAN", "Bibliography", testI18NResolver);
        assertEquals(0, citation.compareTo(copyCitation));
        assertTrue(citationWithoutAuthor.compareTo(citation) <= -1);

    }

}
