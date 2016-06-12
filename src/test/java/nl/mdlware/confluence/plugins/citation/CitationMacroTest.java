package nl.mdlware.confluence.plugins.citation;

import com.atlassian.renderer.v2.RenderMode;
import com.atlassian.renderer.v2.macro.MacroException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Testing {@link CitationMacro}
 */
public class CitationMacroTest {
    private CitationMacro macro;

    @Before
    public void setUp() throws Exception {
        macro = new CitationMacro();
        macro.setI18nResolver(new TestI18NResolver());
    }

    @Test
    public void testCreateCitationMacro() {
        assertEquals(false, macro.hasBody());
        assertEquals(RenderMode.ALL, macro.getBodyRenderMode());
    }

    @Test(expected = MacroException.class)
    public void testEmptyMap() throws MacroException {
        macro.execute(createEmptyParameterMap(), null, null);
    }

    @Test(expected = MacroException.class)
    public void testNullMap() throws MacroException {
        macro.execute(null, null, null);
    }

    @Test(expected = MacroException.class)
    public void testIncompleteMap() throws MacroException {
        macro.execute(createIncompleteParameterMap(), null, null);
    }

    @Test
    public void testMinimalFilledMap() throws MacroException {
        Map params = createMinimalWorkingParameterMap();
        String renderedCitation = macro.execute(params, null, null);
        assertNotNull(renderedCitation);
        assertEquals("<a href='Bibliography#HAN-2000'>[HAN-2000]</a>", renderedCitation);
    }

    @Test
    public void testFullFilledMap() throws MacroException {
        Map params = createMinimalWorkingParameterMap();
        params.put("author", "Rody Middelkoop");
        params.put("publicationDate", "1-1-1999");
        String renderedCitation = macro.execute(params, null, null);
        assertNotNull(renderedCitation);
        assertEquals("<a href='Bibliography#MID-1999'>[MID-1999]</a>", renderedCitation);
    }

    private Map createMinimalWorkingParameterMap() {
        Map params = createEmptyParameterMap();
        params.put("url", "http://www.han.nl/ica");
        params.put("nameOfPage", "ICA");
        params.put("nameOfSite", "HAN");
        params.put("referenceDate", "12-12-2000");
        params.put("bibliographyPage", "Bibliography");
        return params;
    }

    private Map createEmptyParameterMap() {
        return new HashMap();
    }


    private Map createIncompleteParameterMap() {
        Map params = createEmptyParameterMap();
        params.put("url", "http://www.han.nl/ica");
        return params;
    }

}