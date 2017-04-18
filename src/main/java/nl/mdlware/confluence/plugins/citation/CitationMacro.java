package nl.mdlware.confluence.plugins.citation;

import com.atlassian.renderer.RenderContext;
import com.atlassian.renderer.v2.RenderMode;
import com.atlassian.renderer.v2.macro.BaseMacro;
import com.atlassian.renderer.v2.macro.MacroException;
import com.atlassian.sal.api.message.I18nResolver;

import java.util.Map;

/**
 * @author Rody Middelkoop
 */
public class CitationMacro extends BaseMacro {
    public CitationMacro(I18nResolver i18nResolver) {
        this.i18nResolver = i18nResolver;
    }

    public boolean hasBody() {
        return false;
    }

    public String execute(Map params, String body, RenderContext renderContext)
            throws MacroException {
        Citation citation = createCitation(params);
        String content = new RenderedCitation(citation).render();
        return "<a href='viewpage.action?pageId=" + citation.getPageId() +"#" + content + "'>[" + content + "]</a>";
    }

    private Citation createCitation(Map params) throws MacroException {
        Citation citation;
        if (params == null) {
            throw new MacroException(i18nResolver.getText("nl.mdlware.confluence.plugins.citation.confluence-citation-plugin.emptyparameters"));
        }
        try {
            citation = new CitationFactory().createCitationFromMap(params, i18nResolver);
        } catch (IllegalArgumentException iae) {
            throw new MacroException(iae);
        }
        return citation;
    }

    public RenderMode getBodyRenderMode() {
        return RenderMode.ALL;
    }

    private I18nResolver i18nResolver;
}