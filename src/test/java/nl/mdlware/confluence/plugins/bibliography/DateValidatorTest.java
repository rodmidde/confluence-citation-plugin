package nl.mdlware.confluence.plugins.bibliography;

import nl.mdlware.confluence.plugins.citation.DateValidator;
import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.junit.Assert.*;

/**
 * Description for the class DateValidatorTest:
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
public class DateValidatorTest {
    @Test
    public void testValidDate()
    {
        assertTrue(DateValidator.isValidDate("1-1-1900"));
    }

    @Test
    public void testInvalidDate()
    {
        assertFalse(DateValidator.isValidDate("1-11"));
    }

    @Test
    public void testConstructor() throws Exception {
        Constructor<DateValidator> constructor = DateValidator.class.getDeclaredConstructor();
        assertFalse(constructor.isAccessible());
        constructor.setAccessible(true);
        constructor.newInstance(null);
    }


}
