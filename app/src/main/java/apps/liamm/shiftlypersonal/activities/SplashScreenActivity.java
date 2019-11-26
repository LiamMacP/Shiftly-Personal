package apps.liamm.shiftlypersonal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import apps.liamm.shiftlypersonal.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }
}
