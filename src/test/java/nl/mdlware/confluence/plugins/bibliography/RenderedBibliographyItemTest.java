package nl.mdlware.confluence.plugins.bibliography;

import nl.mdlware.confluence.plugins.citation.Citation;
import nl.mdlware.confluence.plugins.citation.RenderedBibliographyItem;
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
        assertEquals("Retrieved 12-12-2000, from HAN: www.han.nl",item.render());
    }

    @Test
    public void testRenderForAuthor() {
        RenderedBibliographyItem item = createRenderedBibliographyItemForAuthor();
        assertEquals("Rody Middelkoop (12-12-1999). ICA. Retrieved 12-12-2000, from HAN: www.han.nl",item.render());
    }

    private RenderedBibliographyItem createRenderedBibliographyItemForAuthor() {
        Citation citation = createCitationWithAuthor();
        RenderedBibliographyItem item = new RenderedBibliographyItem(citation);
        return item;
    }

    private RenderedBibliographyItem createRenderedBibliographyItemWithoutAuthor() {
        Citation citation = createCitationWithoutAuthor();
        RenderedBibliographyItem item = new RenderedBibliographyItem(citation);
        return item;
    }

    private Citation createCitationWithoutAuthor() {
        Citation citation = mock(Citation.class);
        when(citation.getAuthor()).thenReturn("");
        when(citation.getUrl()).thenReturn("www.han.nl");
        when(citation.getReferenceDate()).thenReturn("12-12-2000");
        when(citation.getNameOfSite()).thenReturn("HAN");
        return citation;
    }
    
    private Citation createCitationWithAuthor()
    {
        Citation citation = mock(Citation.class);
        when(citation.getAuthor()).thenReturn("Rody Middelkoop");
        when(citation.getUrl()).thenReturn("www.han.nl");
        when(citation.getReferenceDate()).thenReturn("12-12-2000");
        when(citation.getPublicationDate()).thenReturn("12-12-1999");
        when(citation.getNameOfSite()).thenReturn("HAN");
        when(citation.getNameOfPage()).thenReturn("ICA");
        return citation;
    }



}
