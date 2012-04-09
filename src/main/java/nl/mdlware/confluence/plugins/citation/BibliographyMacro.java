package nl.mdlware.confluence.plugins.citation;

import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.renderer.RenderContext;
import com.atlassian.renderer.v2.RenderMode;
import com.atlassian.renderer.v2.macro.BaseMacro;
import com.atlassian.renderer.v2.macro.MacroException;

import java.io.StringWriter;
import java.util.*;

/**
 * @author Rody Middelkoop
 */
public class BibliographyMacro extends BaseMacro {
    private final PageManager pageManager;
    private final SpaceManager spaceManager;

    public BibliographyMacro(PageManager pageManager, SpaceManager spaceManager) {
        this.pageManager = pageManager;
        this.spaceManager = spaceManager;
    }

    public boolean hasBody() {
        return false;
    }

    public RenderMode getBodyRenderMode() {
        return RenderMode.ALL;
    }

    public String execute(Map params, String body, RenderContext renderContext)
            throws MacroException {
        StringWriter stringWriter = createBasicStringWriter();
        List<Page> pageList = getPages(params);
        if (pageList.size() > 0) {
            renderPageCitations(stringWriter, pageList);
        }
        return stringWriter.toString();
    }

    private StringWriter createBasicStringWriter() {
        StringWriter stringWriter = new StringWriter();
        stringWriter.append("<h1>Bibliography</h1>");
        return stringWriter;
    }

    private List<Page> getPages(Map params) {
        String spaceName = (String) params.get("spaceName");
        return pageManager.getPages(spaceManager.getSpace(spaceName), false);
    }

    private void renderPageCitations(StringWriter stringWriter, List<Page> pageList) {
        List<Citation> citations = new ArrayList<Citation>();
        for (Page page : pageList) {
             citations.addAll(getCitations(page));
        }
        Collections.sort(citations);
        renderCitations(stringWriter, citations);
    }

    private void renderCitations(StringWriter stringWriter, List<Citation> citations) {
        for (Citation citation : citations) {
            String contents = new RenderedCitation(citation).render();
            stringWriter.append("<a name='" + contents + "'>[");
            stringWriter.append(contents);
            stringWriter.append("]</a> ");
            stringWriter.append(new RenderedBibliographyItem(citation).render());
        }
    }

    private List<Citation> getCitations(Page page) {
        List<Citation> citations = new ArrayList<Citation>();
        if (page.getTitle().equals("Home")) {
            Map params = createDefaultMap();
            Citation citation = new CitationFactory().createCitationFromMap(params);
            citations.add(citation);
        }
        return citations;
    }

    private Map createDefaultMap() {
        Map params = new HashMap();
        params.put("url", "http://www.han.nl/ica");
        params.put("nameOfPage", "ICA");
        params.put("nameOfSite", "HAN");
        params.put("referenceDate", "12-12-2000");
        params.put("author", "Rody Middelkoop");
        params.put("publicationDate", "1-1-1999");
        return params;
    }

}