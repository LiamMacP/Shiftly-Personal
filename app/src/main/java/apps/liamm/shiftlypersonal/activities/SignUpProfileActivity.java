package apps.liamm.shiftlypersonal.activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;

import apps.liamm.shiftlypersonal.R;

public class SignUpProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_profile);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar actionBar = getSupportActionBar();

        // Enable the Up button
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

}
