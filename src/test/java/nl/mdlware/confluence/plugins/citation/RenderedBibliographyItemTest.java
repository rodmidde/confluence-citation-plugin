package nl.mdlware.confluence.plugins.citation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Description for the class RenderedBibliographyItemTest:
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
public class RenderedBibliographyItemTest {
    @Test
    public void testRenderWithoutAuthor() {
        RenderedBibliographyItem item = createRenderedBibliographyItemWithoutAuthor();
        assertEquals("Retrieved 12-12-2000, from HAN: <a href='http://www.han.nl'>http://www.han.nl</a>",item.render());
    }

    @Test
    public void testRenderForAuthor() {
        RenderedBibliographyItem item = createRenderedBibliographyItemForAuthor();
        assertEquals("Rody Middelkoop.  (12-12-1999). ICA. Retrieved 12-12-2000, from HAN: <a href='http://www.han.nl'>http://www.han.nl</a>",item.render());
    }

    private RenderedBibliographyItem createRenderedBibliographyItemForAuthor() {
        Citation citation = createCitationWithAuthor();
        return new RenderedBibliographyItem(citation);
    }

    private RenderedBibliographyItem createRenderedBibliographyItemWithoutAuthor() {
        Citation citation = createCitationWithoutAuthor();
        return new RenderedBibliographyItem(citation);
    }

    private Citation createCitationWithoutAuthor() {
        Citation citation = mock(Citation.class);
        when(citation.getAuthor()).thenReturn("");
        when(citation.getUrl()).thenReturn("http://www.han.nl");
        when(citation.getReferenceDate()).thenReturn("12-12-2000");
        when(citation.getNameOfSite()).thenReturn("HAN");
        return citation;
    }
    
    private Citation createCitationWithAuthor()
    {
        Citation citation = mock(Citation.class);
        when(citation.getAuthor()).thenReturn("Rody Middelkoop");
        when(citation.getUrl()).thenReturn("http://www.han.nl");
        when(citation.getReferenceDate()).thenReturn("12-12-2000");
        when(citation.getPublicationDate()).thenReturn("12-12-1999");
        when(citation.getNameOfSite()).thenReturn("HAN");
        when(citation.getNameOfPage()).thenReturn("ICA");
        return citation;
    }



}
