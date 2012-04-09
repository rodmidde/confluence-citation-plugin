package nl.mdlware.confluence.plugins.citation;

import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.renderer.v2.RenderMode;
import com.atlassian.renderer.v2.macro.MacroException;
import nl.mdlware.confluence.plugins.citation.BibliographyMacro;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        assertEquals("<h1>Bibliography</h1>", bibliographyMacro.execute(getRequiredParams(), null, null));
    }

    @Test
    public void testExecuteForMoreThanZeroPagesContainingNoCitations() throws MacroException {
        BibliographyMacro bibliographyMacro = createBibliographyMacro(createFilledListOfPages());
        assertEquals("<h1>Bibliography</h1>", bibliographyMacro.execute(getRequiredParams(), null, null));
    }

    @Test
    public void testExecuteForMoreThanZeroPagesContainingOneCitation() throws MacroException {
        BibliographyMacro bibliographyMacro = createBibliographyMacro(createFilledListOfPagesWithOneCitation());
        assertEquals("<h1>Bibliography</h1><a name='MID-1999'>[MID-1999]</a> Rody Middelkoop (1-1-1999). ICA. Retrieved 12-12-2000, from HAN: <a href='http://www.han.nl/ica'>http://www.han.nl/ica</a><br>", bibliographyMacro.execute(getRequiredParams(), null, null));
    }

    private List<Page> createFilledListOfPagesWithOneCitation() {
        List<Page> pageList = createFilledListOfPages();
        Page page = pageList.get(0);
        page.setTitle("Home");
        page.setBodyAsString("<ac:macro ac:name=\"citation\">\n" +
                "        <ac:parameter ac:name=\"referenceDate\">12-12-2000</ac:parameter>\n" +
                "        <ac:parameter ac:name=\"nameOfPage\">ICA</ac:parameter>\n" +
                "        <ac:parameter ac:name=\"author\">Rody Middelkoop</ac:parameter>\n" +
                "        <ac:parameter ac:name=\"nameOfSite\">HAN</ac:parameter>\n" +
                "        <ac:parameter ac:name=\"url\">http://www.han.nl/ica</ac:parameter>\n" +
                "        <ac:parameter ac:name=\"publicationDate\">1-1-1999</ac:parameter>\n" +
                "    </ac:macro>");
        return pageList;
    }


    private List<Page> createFilledListOfPages() {
        List<Page> pages = createEmptyListOfPages();
        Page page = new Page();
        page.setTitle("Away");
        pages.add(page);
        return pages;
    }

    private Map getRequiredParams() {
        Map params = new HashMap();
        params.put("spaceName", SPACE_KEY);
        return params;
    }

    private BibliographyMacro createBibliographyMacro(List<Page> pageList) {
        SpaceManager spaceManager = mock(SpaceManager.class);
        PageManager pageManager = mock(PageManager.class);
        Space space = mock(Space.class);
        when(spaceManager.getSpace(SPACE_KEY)).thenReturn(space);
        when(pageManager.getPages(space, false)).thenReturn(pageList);
        return new BibliographyMacro(pageManager, spaceManager);

    }

    private List<Page> createEmptyListOfPages() {
        return new ArrayList<Page>();
    }
}
