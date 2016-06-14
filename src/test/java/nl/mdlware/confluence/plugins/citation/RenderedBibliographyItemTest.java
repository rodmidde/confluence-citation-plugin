package nl.mdlware.confluence.plugins.citation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

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
        assertEquals("(12-12-2000). ICA. Retrieved 12-12-2000, from HAN: <a href='http://www.han.nl'>http://www.han.nl</a>", item.render());
    }

    @Test
    public void testRenderWithoutPublicationDate() {
        RenderedBibliographyItem item = createRenderedBibliographyItemWithoutPublicationData();
        assertEquals("Rody Middelkoop. ICA. Retrieved 12-12-2000, from HAN: <a href='http://www.han.nl'>http://www.han.nl</a>", item.render());
    }

    @Test
    public void testRenderForAuthor() {
        RenderedBibliographyItem item = createRenderedBibliographyItemForAuthor();
        assertEquals("Rody Middelkoop.  (12-12-2000). ICA. Retrieved 12-12-2000, from HAN: <a href='http://www.han.nl'>http://www.han.nl</a>", item.render());
    }

    private RenderedBibliographyItem createRenderedBibliographyItemForAuthor() {
        Citation citation = createCompleteCitation();
        return new RenderedBibliographyItem(citation, new TestI18NResolver());
    }

    private RenderedBibliographyItem createRenderedBibliographyItemWithoutAuthor() {
        Citation citation = createCitationWithoutAuthor();
        return new RenderedBibliographyItem(citation, new TestI18NResolver());
    }

    private RenderedBibliographyItem createRenderedBibliographyItemWithoutPublicationData() {
        Citation citation = createCitationWithoutPublicationDate();
        return new RenderedBibliographyItem(citation, new TestI18NResolver());
    }

    private Citation createCitationWithoutAuthor() {
        return new Citation("http://www.han.nl", "", "12-12-2000", "12-12-2000", "ICA", "HAN", "Bieb", new TestI18NResolver());
    }

    private Citation createCitationWithoutPublicationDate() {
        return new Citation("http://www.han.nl", "Rody Middelkoop", "12-12-2000", null, "ICA", "HAN", "Bieb", new TestI18NResolver());
    }

    private Citation createCompleteCitation() {
        return new Citation("http://www.han.nl", "Rody Middelkoop", "12-12-2000", "12-12-2000", "ICA", "HAN", "Bieb", new TestI18NResolver());
    }
}
