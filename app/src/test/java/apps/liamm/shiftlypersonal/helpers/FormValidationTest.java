package apps.liamm.shiftlypersonal.helpers;

import org.junit.Test;

import static apps.liamm.shiftlypersonal.helpers.FormValidation.isValidPassword;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FormValidationTest {

    @Test
    public void testIsValidPassword_noLowercase() {
        final String password = "D3#DDDDD";
        assertFalse(isValidPassword(password));
    }

    @Test
    public void testIsValidPassword_noUppercase() {
        final String password = "d3#ddddd";
        assertFalse(isValidPassword(password));
    }

    @Test
    public void testIsValidPassword_noNumber() {
        final String password = "Dd#ddddd";
        assertFalse(isValidPassword(password));
    }

    @Test
    public void testIsValidPassword_noSymbol() {
        final String password = "s3DDaaaa";
        assertFalse(isValidPassword(password));
    }

    @Test
    public void testIsValidPassword_toShort() {
        final String password = "s3#D";
        assertFalse(isValidPassword(password));
    }

    @Test
    public void testIsValidPassword_validPassword() {
        final String password = "s3#Daaaa";
            assertTrue(isValidPassword(password));
    }
}
