package nl.mdlware.confluence.plugins.citation;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Rody Middelkoop
 */
final class DateValidator {
    private DateValidator() {
    }

    public static boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            sdf.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
