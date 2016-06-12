package nl.mdlware.confluence.plugins.citation;

/**
 * @author Rody Middelkoop
 */
public class CitationExtractionException extends RuntimeException {
    public CitationExtractionException(Exception e) {
        super(e);
    }
}
