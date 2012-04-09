package nl.mdlware.confluence.plugins.citation;

/**
 * Description for the class PageParserException:
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
public class PageParserException extends Exception {
    public PageParserException(Throwable throwable) {
        super(throwable);
    }

    public PageParserException(String message) {
        super(message);
    }
}
