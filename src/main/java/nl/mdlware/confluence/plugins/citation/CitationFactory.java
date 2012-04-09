package nl.mdlware.confluence.plugins.citation;

import nl.mdlware.confluence.plugins.citation.Citation;

import java.util.Map;

/**
 * Description for the class CitationFactory:
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
class CitationFactory {
    public Citation createCitationFromMap(Map params)
    {
        return new Citation(
                getPropAsString(params, "url"),
                getPropAsString(params, "author"),
                getPropAsString(params, "referenceDate"),
                getPropAsString(params, "publicationDate"),
                getPropAsString(params, "nameOfPage"),
                getPropAsString(params, "nameOfSite"));
    }

    private String getPropAsString(Map params, String propName) {
        return (String) params.get(propName);
    }
}
