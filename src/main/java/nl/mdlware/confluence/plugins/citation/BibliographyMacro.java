package nl.mdlware.confluence.plugins.citation;

import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.renderer.PageContext;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.renderer.RenderContext;
import com.atlassian.renderer.v2.RenderMode;
import com.atlassian.renderer.v2.macro.BaseMacro;
import com.atlassian.renderer.v2.macro.MacroException;
import com.atlassian.sal.api.message.I18nResolver;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Rody Middelkoop
 */
public class BibliographyMacro extends BaseMacro {
    private Logger logger = Logger.getLogger(getClass().getName());

    private final PageManager pageManager;
    private final SpaceManager spaceManager;
    private String pageTitle;

    public BibliographyMacro(PageManager pageManager, SpaceManager spaceManager, I18nResolver i18nResolver) {
        this.pageManager = pageManager;
        this.spaceManager = spaceManager;
        this.i18nResolver = i18nResolver;
    }

    public boolean hasBody() {
        return false;
    }

    public RenderMode getBodyRenderMode() {
        return RenderMode.ALL;
    }

    public String execute(Map params, String body, RenderContext renderContext)
            throws MacroException {
        this.pageTitle = ((PageContext)renderContext).getEntity().getTitle();
        StringWriter stringWriter = createBasicStringWriter((String) params.get("pageTitle"));
        List<Page> pageList = getPages(params);
        renderPageCitations(stringWriter, pageList);
        return stringWriter.toString();
    }

    private StringWriter createBasicStringWriter(String pageTitle) {
        StringWriter stringWriter = new StringWriter();
        stringWriter.append("<h2>"+ pageTitle +"</h2>");
        return stringWriter;
    }

    private List<Page> getPages(Map params) {
        String spaceName = (String) params.get("spaceName");
        return pageManager.getPages(spaceManager.getSpace(spaceName), false);
    }

    private void renderPageCitations(StringWriter stringWriter, List<Page> pageList) {
        renderCitations(stringWriter, getSortedCitations(pageList));
    }

    private List<Citation> getSortedCitations(List<Page> pageList) {
        List<Citation> citations = new ArrayList<Citation>();
        for (Page page : pageList) {
            citations.addAll(getCitations(page));
            logger.log(Level.ALL, page.getTitle());
        }
        logger.log(Level.ALL, "Number of pages : " + pageList.size());
        Collections.sort(citations);
        return citations;
    }

    private void renderCitations(StringWriter stringWriter, List<Citation> citations) {
        for (Citation citation : citations) {
            String contents = new RenderedCitation(citation).render();
            stringWriter.append("<a name='" + contents + "'>[");
            stringWriter.append(contents);
            stringWriter.append("]</a> ");
            stringWriter.append(new RenderedBibliographyItem(citation, i18nResolver).render());
            stringWriter.append("<br>");
        }
    }

    private List<Citation> getCitations(Page page) {
        CitationExtractor citationExtractor = new CitationExtractor(page, pageTitle, i18nResolver);
        return citationExtractor.extract();
    }

    private I18nResolver i18nResolver;

}