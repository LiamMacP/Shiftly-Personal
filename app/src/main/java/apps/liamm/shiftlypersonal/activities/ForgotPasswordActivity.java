package apps.liamm.shiftlypersonal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import apps.liamm.shiftlypersonal.R;

import static android.view.WindowManager.LayoutParams;
import static apps.liamm.shiftlypersonal.helpers.FormValidation.isValidEmail;

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {

    private TextInputEditText mEmailEditText;
    @VisibleForTesting
    protected TextView mStatusTextView;
    @VisibleForTesting
    protected ProgressBar mProgressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Toolbar toolbar = findViewById(R.id.forgotpassword_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mEmailEditText = findViewById(R.id.forgotpassword_email_edittext);
        mStatusTextView = findViewById(R.id.forgotpassword_status_textview);
        mProgressBar = findViewById(R.id.forgotpassword_progressbar);

        MaterialButton requestButton = findViewById(R.id.forgotpassord_request_button);
        requestButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        final String email = intent.getStringExtra("EMAIL");
        if (email != null) {
            mEmailEditText.setText(email);
        }
    }

    @Override
    public void onClick(View v) {
        View view = this.getCurrentFocus();
        if (view != null) {
            hideKeyboard(view);
        }

        if (v.getId() == R.id.forgotpassord_request_button) {
            requestPassword();
        }
    }

    /**
     * Handles the showing of the progress bar when the application is attempting to sign in.
     * Stops the user from being able to touch the screen during this process.
     */
    private void showRequestUi() {
        mStatusTextView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        this.screenTouchable(false);
    }

    /**
     * Handles the hiding of the progress bar when the application has finished trying to sign in.
     * Allows the user to touch the screen after attempting to sign in.during this process.
     */
    private void clearRequestUi() {
        mProgressBar.setVisibility(View.GONE);
        this.screenTouchable(true);
        mStatusTextView.setVisibility(View.VISIBLE);
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
        if (!isValidEmail(emailAddress)) {
            mEmailEditText.setError(getString(R.string.invalid_emailaddress));
            return false;
        }

        mEmailEditText.setError(null);
        return true;
    }

    /**
     * Attempt to send a password reset request for the account. Checks both the email and then
     * sends the details to the Firebase Authentication service.
     */
    private void requestPassword() {
        final String emailAddress = Objects.requireNonNull(mEmailEditText.getText()).toString();

        if (checkEmail(emailAddress)) {
            showRequestUi();

            mAuth.sendPasswordResetEmail(emailAddress)
                    .addOnSuccessListener(command -> {
                        mStatusTextView.setText(getText(R.string.forgotpassword_success_message));
                        clearRequestUi();
                    }).addOnFailureListener(e -> {
                        mStatusTextView.setText(getText(R.string.forgotpassword_failed_message));
                        clearRequestUi();
                    });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
