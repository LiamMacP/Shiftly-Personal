package apps.liamm.shiftlypersonal.helpers;

import org.junit.Test;

import static apps.liamm.shiftlypersonal.helpers.FormValidation.IsValidPassword;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FormValidationTest {

    @Test
    public void testIsValidPassword_noLowercase() {
        final String password = "D3#DDDDD";
        assertFalse(IsValidPassword(password));
    }

    @Test
    public void testIsValidPassword_noUppercase() {
        final String password = "d3#ddddd";
        assertFalse(IsValidPassword(password));
    }

    @Test
    public void testIsValidPassword_noNumber() {
        final String password = "Dd#ddddd";
        assertFalse(IsValidPassword(password));
    }

    @Test
    public void testIsValidPassword_noSymbol() {
        final String password = "s3DDaaaa";
        assertFalse(IsValidPassword(password));
    }

    @Test
    public void testIsValidPassword_toShort() {
        final String password = "s3#D";
        assertFalse(IsValidPassword(password));
    }

    @Test
    public void testIsValidPassword_validPassword() {
        final String password = "s3#Daaaa";
            assertTrue(IsValidPassword(password));
    }
}
