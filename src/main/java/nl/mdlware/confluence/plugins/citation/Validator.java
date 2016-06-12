package nl.mdlware.confluence.plugins.citation;

/**
 * @author Rody Middelkoop
 */
final class Validator {
    private Validator() {
    }

    public static boolean isSet(Object field) {
        return field != null && !"".equals(field);
    }
}
