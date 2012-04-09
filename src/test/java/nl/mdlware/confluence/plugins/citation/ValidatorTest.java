package nl.mdlware.confluence.plugins.citation;

import nl.mdlware.confluence.plugins.citation.Validator;
import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Description for the class ValidatorTest:
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
public class ValidatorTest {
    @Test
    public void testIsSet() {
        assertFalse(Validator.isSet(null));
        assertFalse(Validator.isSet(""));
        assertTrue(Validator.isSet("SET"));
    }


    @Test
    public void testConstructor() throws Exception {
        Constructor<Validator> constructor = Validator.class.getDeclaredConstructor();
        assertFalse(constructor.isAccessible());
        constructor.setAccessible(true);
        constructor.newInstance(null);
    }
}
