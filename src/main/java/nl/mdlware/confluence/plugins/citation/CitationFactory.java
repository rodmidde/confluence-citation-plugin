package nl.mdlware.confluence.plugins.citation;

import com.atlassian.sal.api.message.I18nResolver;

import java.util.Map;

/**
 * @author Rody Middelkoop
 */
class CitationFactory {
    public Citation createCitationFromMap(Map params, I18nResolver i18n) {
        return new Citation(
                getPropAsString(params, "url"),
                getPropAsString(params, "author"),
                getPropAsString(params, "referenceDate"),
                getPropAsString(params, "publicationDate"),
                getPropAsString(params, "nameOfPage"),
                getPropAsString(params, "nameOfSite"),
                getPropAsString(params, "pageId"), i18n);
    }

    private String getPropAsString(Map params, String propName) {
        return (String) params.get(propName);
    }
}
