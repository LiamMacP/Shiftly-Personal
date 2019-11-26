package apps.liamm.shiftlypersonal.helpers;

import androidx.annotation.NonNull;

public class StringUtils {

    /**
     * Check to see if the string value inputted is either null or empty.
     *
     * @param value the string value you want to check.
     * @return return true if null or empty, else false.
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

}
