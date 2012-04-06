package nl.mdlware.confluence.plugins.bibliography;

import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.renderer.RenderContext;
import com.atlassian.renderer.v2.RenderMode;
import com.atlassian.renderer.v2.macro.BaseMacro;
import com.atlassian.renderer.v2.macro.MacroException;

import java.util.Map;

/**
 * @author Rody Middelkoop
 */
public class CitationMacro extends BaseMacro {
    private final PageManager pageManager;
    private final SpaceManager spaceManager;

    public CitationMacro(PageManager pageManager, SpaceManager spaceManager) {
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
        Citation citation = createCitation(params);
        String content = new RenderedCitation(citation).render();
        return "<a href='Bibliography#" + content + "'>[" + content + "]</a>";
    }

    private Citation createCitation(Map params) throws MacroException {
        Citation citation = null;
        if (params == null || params.isEmpty()) throw new MacroException("Empty parameterlist");
        try {
            citation = new Citation(
                    getPropAsString(params, "url"),
                    getPropAsString(params, "author"),
                    getPropAsString(params, "referenceDate"),
                    getPropAsString(params, "publicationDate"),
                    getPropAsString(params, "nameOfPage"),
                    getPropAsString(params, "nameOfSite"));
        } catch (IllegalArgumentException iae) {
            throw new MacroException(iae);
        }
        return citation;
    }

    private String getPropAsString(Map params, String propName) {
        return (String) params.get(propName);
    }

}