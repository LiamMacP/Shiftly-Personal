package apps.liamm.shiftlypersonal.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    public void hideKeyboard(@NonNull View view) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
