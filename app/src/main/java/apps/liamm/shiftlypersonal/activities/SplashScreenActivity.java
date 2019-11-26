package apps.liamm.shiftlypersonal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import apps.liamm.shiftlypersonal.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = SplashScreenActivity.class.getSimpleName();

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        Intent nextLocationIntent;
        if (currentUser != null) {
            nextLocationIntent =
                    new Intent(SplashScreenActivity.this, DashboardActivity.class);
        } else {
            nextLocationIntent =
                    new Intent(SplashScreenActivity.this, SignInActivity.class);
        }

        startActivity(nextLocationIntent);
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }
}
