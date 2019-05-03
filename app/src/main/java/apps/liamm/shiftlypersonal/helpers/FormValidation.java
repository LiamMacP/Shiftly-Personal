package apps.liamm.shiftlypersonal.helpers;

import android.util.Patterns;

import androidx.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks specific inputs from forms to check for the validity.
 *
 * Currently contains checks for both emails and passwords.
 */
public final class FormValidation {

    /**
     * Checks to see if the email address is in compliance with built in regex for email addresses.
     *
     * @see Patterns#EMAIL_ADDRESS for a description of the regular expression used.
     * @param emailAddress the email address we want to check
     * @return true if the email is valid, else false.
     */
    public static boolean IsValidEmail(@NonNull final String emailAddress) {
        return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }

    /**
     * Checks to see if the password is valid or not by a the following rules:
     *
     *      Length is greater than 8
     *      Contains 1 lowercase alphabetical character
     *      Contains 1 uppercase alphabetical character
     *      Contains 1 numeric character
     *      Contains 1 special character
     *
     * @param password the password to check if valid or not.
     * @return true if the password is valid, else false.
     */
    public static boolean IsValidPassword(@NonNull final String password) {
        final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*#?&]{8,}$";

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
