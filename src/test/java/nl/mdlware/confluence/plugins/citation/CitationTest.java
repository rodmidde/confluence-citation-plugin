package nl.mdlware.confluence.plugins.citation;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.rules.ExpectedException;

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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreateCitationWithEmptyURL() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(CoreMatchers.containsString("Empty"));
        new Citation("", "author", "somedate", "somedate", "page", "site", "Bibliography", testI18NResolver);
    }

    @Test
    public void testCreateCitationWithNullURL() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(CoreMatchers.containsString("Empty"));
        new Citation(null, "author", "somedate", "somedate", "page", "site", "Bibliography", testI18NResolver);
    }

    @Test
    public void testCreateCitationWithNullReferenceDate() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(CoreMatchers.containsString("Empty"));
        new Citation("url", "author", null, "", "page", "site", "Bibliography", testI18NResolver);
    }
    @Test
    public void testCreateCitationWithEmptyReferenceDate() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(CoreMatchers.containsString("Empty"));
        new Citation("url", "author", "", "", "page", "site", "Bibliography", testI18NResolver);
    }

    @Test
    public void testCreateCitationWithInvalidReferenceDate() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(CoreMatchers.containsString("Invalid"));
        new Citation("url", "author", "1234", "", "page", "site", "Bibliography", testI18NResolver);
    }

    @Test
    public void testCreateCitationWithInvalidPublicationDate() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(CoreMatchers.containsString("Invalid"));
        new Citation("url", "author", "1-1-2000", "1234", "page", "site", "Bibliography", testI18NResolver);
    }

    @Test
    public void testCreateCitationWithEmptyPagename() {
        thrown.expectMessage(CoreMatchers.containsString("Empty"));
        thrown.expect(IllegalArgumentException.class);
        new Citation("url", "author", "somedate", "somedate", "", "site", "Bibliography", testI18NResolver);
    }

    @Test
    public void testCreateCitationWithNullPagename() {
        thrown.expectMessage(CoreMatchers.containsString("Empty"));
        thrown.expect(IllegalArgumentException.class);
        new Citation("url", "author", "somedate", "somedate", null, "site", "Bibliography", testI18NResolver);
    }

    @Test
    public void testCreateCitationWithEmptySitename() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(CoreMatchers.containsString("Empty"));
        new Citation("url", "author", "somedate", "somedate", "page", "", "Bibliography", testI18NResolver);
    }

    @Test
    public void testCreateCitationWithNullSitename() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(CoreMatchers.containsString("Empty"));
        new Citation("url", "author", "somedate", "somedate", "page", null, "Bibliography", testI18NResolver);
    }

    @Test
    public void testCreateCitationWithEmptyBibPage() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(CoreMatchers.containsString("Empty"));
        new Citation("url", "author", "somedate", "somedate", "page", "site", "", testI18NResolver);
    }

    @Test
    public void testCreateCitationWithNullBibPage() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(CoreMatchers.containsString("Empty"));
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
