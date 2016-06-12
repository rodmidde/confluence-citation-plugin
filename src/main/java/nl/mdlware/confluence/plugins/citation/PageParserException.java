package nl.mdlware.confluence.plugins.citation;

/**
 * @author Rody Middelkoop
 */
public class PageParserException extends Exception {
    public PageParserException(Throwable throwable) {
        super(throwable);
    }

    public PageParserException(String message) {
        super(message);
    }
}
