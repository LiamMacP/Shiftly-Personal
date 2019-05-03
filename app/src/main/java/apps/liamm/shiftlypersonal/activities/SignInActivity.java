package apps.liamm.shiftlypersonal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import apps.liamm.shiftlypersonal.R;

import static apps.liamm.shiftlypersonal.helpers.FormValidation.IsValidEmail;
import static apps.liamm.shiftlypersonal.helpers.FormValidation.IsValidPassword;

public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = SignInActivity.class.getSimpleName();

    private TextInputEditText mEmailEditText;
    private TextInputEditText mPasswordEditView;
    @VisibleForTesting
    protected ProgressBar mProgressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mEmailEditText = findViewById(R.id.signin_email_edittext);
        mPasswordEditView = findViewById(R.id.signin_password_edittext);
        MaterialButton mSignInButton = findViewById(R.id.signin_request_button);
        MaterialButton mSignUpButton = findViewById(R.id.signin_signup_button);
        TextView mForgotPasswordButton = findViewById(R.id.signin_forgotpassword_button);
        mProgressBar = findViewById(R.id.signin_progrsessbar);

        mSignInButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);
        mForgotPasswordButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        showProgressBar();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent dashboardIntent =
                    new Intent(SignInActivity.this, DashboardActivity.class);
            startActivity(dashboardIntent);
        }

        hideProgressBar();
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * Checks to see if the email address is valid.
     * <p>
     * If the email address is not valid, display an error to the user explaining its wrong.
     *
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
     * <p>
     * If the password is not valid, display an error to the user explaining its wrong.
     *
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

    @Override
    public void onClick(@NonNull View v) {
        View view = this.getCurrentFocus();
        if (view != null) {
            hideKeyboard(view);
        }

        switch (v.getId()) {
            case R.id.signin_signup_button:
                final Intent signUpIntent =
                        new Intent(SignInActivity.this, SignUpActivity.class);
                signUpIntent.putExtra("EMAIL",
                        Objects.requireNonNull(mEmailEditText.getText()).toString());
                startActivity(signUpIntent);
                break;
            case R.id.signin_request_button:
                signIn();
                break;
            case R.id.signin_forgotpassword_button:
                final Intent forgotPasswordIntent =
                        new Intent(SignInActivity.this, ForgotPasswordActivity.class);
                forgotPasswordIntent.putExtra("EMAIL",
                        Objects.requireNonNull(mEmailEditText.getText()).toString());
                startActivity(forgotPasswordIntent);
                break;
        }
    }

    private void signIn() {
        final String emailAddress = Objects.requireNonNull(mEmailEditText.getText()).toString();
        final String password = Objects.requireNonNull(mPasswordEditView.getText()).toString();

        if (checkEmail(emailAddress)
                && checkPassword(password)) {
            showProgressBar();

            mAuth.signInWithEmailAndPassword(emailAddress, password).addOnSuccessListener(
                    authResult -> {
                        hideProgressBar();
                        Intent dashboardIntent
                            = new Intent(SignInActivity.this, DashboardActivity.class);
                        startActivity(dashboardIntent);
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "signIn:Failed to sign in. ", e);

                        hideProgressBar();
                        Toast.makeText(this,
                                getString(R.string.signin_authenticationfailed),
                                Toast.LENGTH_SHORT)
                                .show();
                    });
        }
    }
}
