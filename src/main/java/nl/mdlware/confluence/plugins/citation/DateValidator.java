package nl.mdlware.confluence.plugins.citation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description for the class DateValidator:
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
class DateValidator {
    private DateValidator() {
    }

    public static boolean isValidDate(String date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try
        {
            sdf.parse(date);
        }
        catch (ParseException e)
        {
            return false;
        }
        return true;
    }
}
