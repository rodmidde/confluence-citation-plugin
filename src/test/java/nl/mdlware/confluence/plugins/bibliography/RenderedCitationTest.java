package nl.mdlware.confluence.plugins.bibliography;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Description for the class RenderedCitationTest:
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
public class RenderedCitationTest {
    @Test
    public void testRenderForAuthor() {
        RenderedCitation renderedCitation = new RenderedCitation(createMockedCitationForAuthor());
        assertEquals("MID-2000", renderedCitation.render());
    }

    @Test
    public void testRenderForAuthorWithMissingFirstname() {
        RenderedCitation renderedCitation = new RenderedCitation(createMockedCitationForAuthorWithMissingFirstname());
        assertEquals("MID-2000", renderedCitation.render());
    }

    @Test
    public void testRenderForAuthorWithShortLastname() {
        RenderedCitation renderedCitation = new RenderedCitation(createMockedCitationForAuthorWithShortLastname());
        assertEquals("ES-2000", renderedCitation.render());
    }

    @Test
    public void testRenderWithoutAuthor() {
        RenderedCitation renderedCitation = new RenderedCitation(createMockedCitationWithoutAuthor());
        assertEquals("HAN-2000", renderedCitation.render());
    }

    @Test
    public void testRenderOnEmptyCitation()
    {
        Citation citation = mock(Citation.class);
        RenderedCitation renderedCitation = new RenderedCitation(citation);
        assertEquals("null-null", renderedCitation.render());
    }

    private Citation createMockedCitationForAuthor() {
        Citation citation = mock(Citation.class);
        when(citation.getAuthor()).thenReturn("Rody Middelkoop");
        when(citation.getReferenceDate()).thenReturn("12-12-2000");
        return citation;
    }

    private Citation createMockedCitationForAuthorWithMissingFirstname() {
        Citation citation = mock(Citation.class);
        when(citation.getAuthor()).thenReturn("Middelkoop");
        when(citation.getReferenceDate()).thenReturn("12-12-2000");
        return citation;
    }

    private Citation createMockedCitationForAuthorWithShortLastname() {
        Citation citation = mock(Citation.class);
        when(citation.getAuthor()).thenReturn("Es");
        when(citation.getReferenceDate()).thenReturn("12-12-2000");
        return citation;
    }

    private Citation createMockedCitationWithoutAuthor() {
        Citation citation = mock(Citation.class);
        when(citation.getAuthor()).thenReturn("");
        when(citation.getReferenceDate()).thenReturn("12-12-2000");
        when(citation.getNameOfSite()).thenReturn("HAN");
        return citation;
    }
}
