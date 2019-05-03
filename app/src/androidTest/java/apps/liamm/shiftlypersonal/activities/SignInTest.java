package apps.liamm.shiftlypersonal.activities;

import android.view.View;
import android.widget.ProgressBar;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import apps.liamm.shiftlypersonal.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignInTest {

    @Rule
    public ActivityTestRule<SignInActivity> mActivityTestRule =
            new ActivityTestRule<>(SignInActivity.class);
    private IdlingResource mActivityResource;

    @Before
    public void setUp() {
        if (mActivityResource != null) {
            IdlingRegistry.getInstance().unregister(mActivityResource);
        }

        mActivityResource = new SignInIdlingResource(mActivityTestRule.getActivity());
        IdlingRegistry.getInstance().register(mActivityResource);
    }

    @After
    public void tearDown() {
        if (mActivityResource != null) {
            IdlingRegistry.getInstance().unregister(mActivityResource);
        }
    }

    @Test
    public void failedSignInTest() {
        String email = "test@example.com";
        String password = "aB#3aaab";

        // Make sure we're signed out
        signOutIfPossible();

        // Enter email
        enterEmail(email);

        // Enter password
        enterPassword(password);

        // Click sign in
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.signin_request_button), withText(R.string.signin_request_button),
                        isDisplayed()));
        appCompatButton.perform(click());

        assert (FirebaseAuth.getInstance().getCurrentUser() == null);

        signOutIfPossible();
    }

    @Test
    public void testSignIn_Successful() {
        String email = "test@example.com";
        String password = "aB#3aaaa";

        // Make sure we're signed out
        signOutIfPossible();

        // Enter email
        enterEmail(email);

        // Enter password
        enterPassword(password);

        // Click sign in
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.signin_request_button), withText(R.string.signin_request_button),
                        isDisplayed()));
        appCompatButton.perform(click());

        assert (FirebaseAuth.getInstance().getCurrentUser() != null);

        signOutIfPossible();
    }

    private void enterEmail(String email) {
        ViewInteraction emailField = onView(
                allOf(withId(R.id.signin_email_edittext),
                        isDisplayed()));
        emailField.perform(replaceText(email));
    }

    private void enterPassword(String password) {
        ViewInteraction passwordField = onView(
                allOf(withId(R.id.signin_password_edittext),
                        isDisplayed()));
        passwordField.perform(replaceText(password));
    }

    private void signOutIfPossible() {
        FirebaseAuth.getInstance().signOut();
    }


    public class SignInIdlingResource implements IdlingResource {

        private SignInActivity mActivity;
        private ResourceCallback mCallback;

        SignInIdlingResource(SignInActivity activity) {
            mActivity = activity;
        }

        @Override
        public String getName() {
            return "SignInIdlingResource:" + mActivity.getLocalClassName();
        }

        @Override
        public boolean isIdleNow() {
            ProgressBar progressBar = mActivity.mProgressBar;
            boolean idle = (progressBar == null || progressBar.getVisibility() == View.GONE);

            if (mCallback != null && idle) {
                mCallback.onTransitionToIdle();
            }

            return idle;
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback callback) {
            mCallback = callback;
        }

    }

}
