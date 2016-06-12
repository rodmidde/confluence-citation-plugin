package nl.mdlware.confluence.plugins.citation;

import com.atlassian.renderer.RenderContext;
import com.atlassian.renderer.v2.RenderMode;
import com.atlassian.renderer.v2.macro.BaseMacro;
import com.atlassian.renderer.v2.macro.MacroException;

import java.util.Map;

/**
 * @author Rody Middelkoop
 */
public class CitationMacro extends BaseMacro {
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
        Citation citation;
        if (params == null || params.isEmpty()) {
            throw new MacroException("Empty parameterlist");
        }
        try {
            citation = new CitationFactory().createCitationFromMap(params);
        } catch (IllegalArgumentException iae) {
            throw new MacroException(iae);
        }
        return citation;
    }



}