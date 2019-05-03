package apps.liamm.shiftlypersonal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;

import static apps.liamm.shiftlypersonal.helpers.FormValidation.IsValidEmail;
import static apps.liamm.shiftlypersonal.helpers.FormValidation.IsValidPassword;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText mEmailEditText;
    private TextInputEditText mPasswordEditView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mEmailEditText = findViewById(R.id.signin_email_edittext);
        mPasswordEditView = findViewById(R.id.signin_password_edittext);
    }

    /**
     * Checks to see if the email address is valid.
     *
     * If the email address is not valid, display an error to the user explaining its wrong.
     * @param emailAddress the email address string that we want to check.
     * @return true if the email is valid, else false.
     */
    private boolean checkEmail(@NonNull final String emailAddress) {
        if (!IsValidEmail(emailAddress)) {
            mEmailEditText.setError(getString(R.string.signin_invalid_emailaddress));
            return false;
        }

        mEmailEditText.setError(null);
        return true;
    }

    /**
     * Checks to see if the password is valid.
     *
     * If the password is not valid, display an error to the user explaining its wrong.
     * @param password the password string that we want to check.
     * @return true if the password is valid, else false.
     */
    private boolean checkPassword(@NonNull final String password) {
        if (!IsValidPassword(password)) {
            mPasswordEditView.setError(getString(R.string.signin_invalid_password));
            return false;
        }

        mPasswordEditView.setError(null);
        return true;
    }
}
