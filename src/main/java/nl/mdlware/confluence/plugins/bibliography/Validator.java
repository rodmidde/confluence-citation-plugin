package nl.mdlware.confluence.plugins.bibliography;

/**
 * Description for the class Validator:
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
public class Validator {
    static boolean isSet(Object field) {
        return field != null && !"".equals(field);
    }
}
