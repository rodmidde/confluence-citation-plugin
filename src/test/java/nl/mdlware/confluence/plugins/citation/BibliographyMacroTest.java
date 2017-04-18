package nl.mdlware.confluence.plugins.citation;

import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.renderer.PageContext;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.renderer.RenderContext;
import com.atlassian.renderer.v2.RenderMode;
import com.atlassian.renderer.v2.macro.MacroException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nl.mdlware.confluence.plugins.citation.FileContentAware.readFileAsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Description for the class BibliographyMacroTest:
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
public class BibliographyMacroTest {
    private static final String SPACE_KEY = "ds";
    public static final String H2_BIBLIOGRAPHY_H2 = "<h2>Bibliography</h2>";

    @Test
    public void testCreateBibliographyMacro() {
        BibliographyMacro macro = createBibliographyMacro(createEmptyListOfPages());
        assertEquals(false, macro.hasBody());
        assertEquals(RenderMode.ALL, macro.getBodyRenderMode());
    }

    @Test
    public void testExecuteForZeroPages() throws MacroException {
        List<Page> pageList = createEmptyListOfPages();
        BibliographyMacro bibliographyMacro = createBibliographyMacro(pageList);
        assertEquals(H2_BIBLIOGRAPHY_H2, bibliographyMacro.execute(getRequiredParams(), null, getPageContext()));
    }

    @Test
    public void testExecuteForMoreThanZeroPagesContainingNoCitations() throws MacroException {
        BibliographyMacro bibliographyMacro = createBibliographyMacro(createFilledListOfPages());
        assertEquals(H2_BIBLIOGRAPHY_H2, bibliographyMacro.execute(getRequiredParams(), null, getPageContext()));
    }

    private PageContext getPageContext() {
        Page page = mock(Page.class);
        when(page.getTitle()).thenReturn("Bibliography");
        when(page.getId()).thenReturn(12345678L);

        return new PageContext(page);
    }

    @Test
    public void testExecuteForMoreThanZeroPagesContainingOneCitation() throws MacroException {
        BibliographyMacro bibliographyMacro = createBibliographyMacro(createFilledListOfPagesWithOneCitation());
        assertEquals(H2_BIBLIOGRAPHY_H2 + "<a name='MID-1999'>[MID-1999]</a> Rody Middelkoop.  (1-1-1999). ICA. Retrieved 12-12-2000, from HAN: <a href='http://www.han.nl/ica'>http://www.han.nl/ica</a><br>", bibliographyMacro.execute(getRequiredParams(), null, getPageContext()));
    }

    @Test
    public void testExecuteForSpaceThatHasSeveralPagesWithCitations() throws MacroException {
        BibliographyMacro bibliographyMacro = createBibliographyMacro(createLongListOfPagesWithTwoCitations());
        assertEquals(H2_BIBLIOGRAPHY_H2 + "<a name='MID-2010'>[MID-2010]</a> Rody Middelkoop.  (1-1-2010). DDOA. Retrieved 9-4-2012, from DDOA: <a href='http://wiki.icaprojecten.nl'>http://wiki.icaprojecten.nl</a><br><a name='MID-2011'>[MID-2011]</a> Rody Middelkoop.  (1-1-2011). ICA. Retrieved 12-12-2012, from HAN: <a href='http://www.han.nl/ica'>http://www.han.nl/ica</a><br>", bibliographyMacro.execute(getRequiredParams(), null, getPageContext()));
    }

    private List<Page> createLongListOfPagesWithTwoCitations() {
        List<Page> pages = createEmptyListOfPages();
        Page page1 = createPage("Bibliography", "bigpage-nocitations-subpages.xml");
        pages.add(page1);
        Page page2 = createPage("Bibliography", "two-citations.xml");
        pages.add(page2);
        return pages;
    }

    private Page createPage(String title, String fileName) {
        Page page = new Page();
        page.setTitle(title);
        page.setId(12345678L);
        page.setBodyAsString(readFileAsString(fileName));
        return page;
    }

    private List<Page> createFilledListOfPagesWithOneCitation() {
        List<Page> pageList = createFilledListOfPages();
        Page page = pageList.get(0);
        page.setId(12345678L);
        page.setTitle("Bibliography");
        page.setBodyAsString("<ac:structured-macro ac:name=\"citation\">\n" +
                "        <ac:parameter ac:name=\"referenceDate\">12-12-2000</ac:parameter>\n" +
                "        <ac:parameter ac:name=\"nameOfPage\">ICA</ac:parameter>\n" +
                "        <ac:parameter ac:name=\"author\">Rody Middelkoop</ac:parameter>\n" +
                "        <ac:parameter ac:name=\"nameOfSite\">HAN</ac:parameter>\n" +
                "        <ac:parameter ac:name=\"url\">http://www.han.nl/ica</ac:parameter>\n" +
                "        <ac:parameter ac:name=\"publicationDate\">1-1-1999</ac:parameter>\n" +
                "        <ac:parameter ac:name=\"pageId\"><link><ri:page ri:content-title=\"12345678\" /></link></ac:parameter>\n" +
                "    </ac:structured-macro>");
        return pageList;
    }


    private List<Page> createFilledListOfPages() {
        List<Page> pages = createEmptyListOfPages();
        Page page = new Page();
        page.setTitle("Bibliography");
        page.setId(12345678L);
        pages.add(page);
        return pages;
    }

    private Map getRequiredParams() {
        Map params = new HashMap();
        params.put("spaceName", SPACE_KEY);
        params.put("pageId", "12345678");

        return params;
    }

    private BibliographyMacro createBibliographyMacro(List<Page> pageList) {
        SpaceManager spaceManager = mock(SpaceManager.class);
        PageManager pageManager = mock(PageManager.class);
        Space space = mock(Space.class);
        when(spaceManager.getSpace(SPACE_KEY)).thenReturn(space);
        when(pageManager.getPages(space, false)).thenReturn(pageList);
        Page page = mock(Page.class);
        when(page.getTitle()).thenReturn("Bibliography");
        when(pageManager.getPage(12345678L)).thenReturn(page);
        BibliographyMacro macro = new BibliographyMacro(pageManager, spaceManager, new TestI18NResolver());
        return macro;

    }

    private List<Page> createEmptyListOfPages() {
        return new ArrayList<Page>();
    }
}
