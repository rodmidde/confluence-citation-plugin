package nl.mdlware.confluence.plugins.citation;

/**
 * @author Rody Middelkoop
 */
public class XMLDocumentWrapper {
    public String wrapIntoValidXML(String pageContents) {
        return String.format("<?xml version=\"1.0\"?><!DOCTYPE some_name [<!ENTITY nbsp \"&#160;\">" +
                "<!ENTITY ndash   \"&#8211;\"><!ENTITY mdash   \"&#8212;\">" +
                "<!ENTITY rsquo   \"&#8217;\"><!ENTITY lsquo   \"&#8216;\">" +
                "<!ENTITY ldquo   \"&#8220;\">" +
                "<!ENTITY rdquo   \"&#8221;\">" +
                "]><p>%s</p>", pageContents);
    }
}
