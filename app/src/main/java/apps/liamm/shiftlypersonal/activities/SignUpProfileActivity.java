package apps.liamm.shiftlypersonal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import apps.liamm.shiftlypersonal.R;
import apps.liamm.shiftlypersonal.helpers.FormValidation;
import apps.liamm.shiftlypersonal.helpers.StringUtils;

public class SignUpProfileActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = SignUpProfileActivity.class.getSimpleName();

    private FirebaseAuth mAuth;

    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mRepeatPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_profile);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        mNameEditText = findViewById(R.id.signup_name_edittext);
        mEmailEditText = findViewById(R.id.signup_email_edittext);
        mPasswordEditText = findViewById(R.id.signup_password_edittext);
        mRepeatPasswordEditText = findViewById(R.id.signin_repeatpassword_edittext);

        mAuth = FirebaseAuth.getInstance();

        Intent receivedIntent = getIntent();
        mEmailEditText.setText(receivedIntent.getStringExtra("EMAIL"));

        findViewById(R.id.signup_request_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signup_request_button) {
            if (verifyUserInformation()
                    && verifyPassword()) {
                createAccount();
            }
        }
    }

    private void createAccount() {
        final String name = mNameEditText.getText().toString().trim();
        final String email = mEmailEditText.getText().toString().trim();
        final String password = mPasswordEditText.getText().toString().trim();

        this.screenTouchable(false);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(command -> {
                    Log.d(TAG, "signUp: Successfully created user.");

                    updateUserName(name);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "signUp: Failed to create an account, please try again later.", e);

                    Toast.makeText(SignUpProfileActivity.this, "Failed to create an account, please try again later.", Toast.LENGTH_LONG).show();
                });
    }

    private void updateUserName(@NonNull String name) {
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;

        UserProfileChangeRequest profileChangeRequest
                = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
        user.updateProfile(profileChangeRequest)
                .addOnSuccessListener(command -> {
                    Intent intent = new Intent(SignUpProfileActivity.this, SetWorkplaceActivity.class);
                    intent.putExtra("ACCOUNT_CREATION", true);
                    startActivity(intent);
                })
                .addOnFailureListener(command -> user.delete());
    }

    private boolean verifyUserInformation() {
        Log.d(TAG, "verifyUserInformation: Attemtpting to verify user information.");

        final String name = mNameEditText.getText().toString().trim();
        final String email = mEmailEditText.getText().toString().toLowerCase().trim();

        if (StringUtils.isNullOrEmpty(name))
        {
            mNameEditText.setError("Forename field cannot be empty.");
            return false;
        } else {
            mNameEditText.setError(null);
        }

        if (StringUtils.isNullOrEmpty(email)) {
            mEmailEditText.setError("Email field cannot be empty.");
            return false;
        } else if (!FormValidation.isValidEmail(email)) {
            mEmailEditText.setError("Email is not valid.");
            return false;

        } else {
            mEmailEditText.setError(null);
        }

        return true;
    }

    private boolean verifyPassword() {
        Log.d(TAG, "verifyPassword: Attempting to validate password.");

        final String password = mPasswordEditText.getText().toString().trim();
        final String confirmPassword = mRepeatPasswordEditText.getText().toString().trim();

        if (StringUtils.isNullOrEmpty(password)) {
            mPasswordEditText.setError("Password field cannot be empty.");
            return false;
        } else if (!FormValidation.isValidPassword(password)) {
            mPasswordEditText.setError("Passwords have to be between 8 and 24 length and only" +
                    " allows alphanumeric characters aswell as !,#,$,@");
            return false;
        } else {
            mPasswordEditText.setError(null);
        }

        if (StringUtils.isNullOrEmpty(confirmPassword)) {
            mRepeatPasswordEditText.setError("Confirm password field cannot be empty");
            return false;
        } else {
            mRepeatPasswordEditText.setError(null);
        }

        if (!password.equals(confirmPassword)) {
            mRepeatPasswordEditText.setError("Passwords do not match.");
            return false;
        } else {
            mRepeatPasswordEditText.setError(null);
        }

        return true;
    }

}
