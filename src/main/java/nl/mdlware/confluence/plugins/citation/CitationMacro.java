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
    public boolean hasBody() {
        return false;
    }

    public String execute(Map params, String body, RenderContext renderContext)
            throws MacroException {
        Citation citation = createCitation(params);
        String content = new RenderedCitation(citation).render();
        return "<a href='" + citation.getBibliographyPage() +"#" + content + "'>[" + content + "]</a>";
    }

    private Citation createCitation(Map params) throws MacroException {
        Citation citation;
        if (params == null || params.isEmpty()) {
            throw new MacroException(i18n.getText("nl.mdlware.confluence.plugins.citation.confluence-citation-plugin.emptyparameters"));
        }
        try {
            citation = new CitationFactory().createCitationFromMap(params, i18n);
        } catch (IllegalArgumentException iae) {
            throw new MacroException(iae);
        }
        return citation;
    }

    public RenderMode getBodyRenderMode() {
        return RenderMode.ALL;
    }

    public void setI18nResolver(I18nResolver i18n) {
        this.i18n = i18n;
    }

    private I18nResolver i18n;
}