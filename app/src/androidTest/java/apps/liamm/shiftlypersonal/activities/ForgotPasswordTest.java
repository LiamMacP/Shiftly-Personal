package apps.liamm.shiftlypersonal.activities;

import android.view.View;
import android.widget.ProgressBar;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import apps.liamm.shiftlypersonal.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static apps.liamm.shiftlypersonal.activities.CustomHamcrestMatchers.hasTextInputLayoutHintText;
import static org.hamcrest.CoreMatchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ForgotPasswordTest {

    @Rule
    public ActivityTestRule<ForgotPasswordActivity> mActivityTestRule =
            new ActivityTestRule<>(ForgotPasswordActivity.class);
    private IdlingResource mActivityResource;

    @Before
    public void setUp() {
        if (mActivityResource != null) {
            IdlingRegistry.getInstance().unregister(mActivityResource);
        }

        mActivityResource = new ForgotPasswordIdlingResource(mActivityTestRule.getActivity());
        IdlingRegistry.getInstance().register(mActivityResource);
    }

    @After
    public void tearDown() {
        if (mActivityResource != null) {
            IdlingRegistry.getInstance().unregister(mActivityResource);
        }
    }

    @Test
    public void testForgotPassword_Success() {
        String email = "test@example.com";

        // Enter email
        enterEmail(email);

        // Click send request
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.forgotpassord_request_button),
                        withText(R.string.forgotpassword_request_button),
                        isDisplayed()));
        appCompatButton.perform(click());

        onView(withId(R.id.forgotpassword_status_textview))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.forgotpassword_success_message)));
    }

    @Test
    public void testForgotPassword_Failed() {
        String email = "test@example1.com";

        // Enter email
        enterEmail(email);

        // Click send request
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.forgotpassord_request_button),
                        withText(R.string.forgotpassword_request_button),
                        isDisplayed()));
        appCompatButton.perform(click());

        onView(withId(R.id.forgotpassword_status_textview))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.forgotpassword_failed_message)));
    }

    @Test
    public void testForgotPassword_EmailErrorMessage() {
        String email = "testexample.com";

        // Enter email
        enterEmail(email);

        // Click sign in
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.forgotpassord_request_button), withText(R.string.forgotpassword_request_button),
                        isDisplayed()));
        appCompatButton.perform(click());

        onView(allOf(withId(R.id.forgotpassword_email_edittext),
                hasTextInputLayoutHintText(InstrumentationRegistry.getInstrumentation().getTargetContext().getString(R.string.invalid_emailaddress)),
                isDisplayed()));
    }

    private void enterEmail(String email) {
        ViewInteraction emailField = onView(
                allOf(withId(R.id.forgotpassword_email_edittext),
                        isDisplayed()));
        emailField.perform(replaceText(email));
    }

    public class ForgotPasswordIdlingResource implements IdlingResource {

        private ForgotPasswordActivity mActivity;
        private ResourceCallback mCallback;

        ForgotPasswordIdlingResource(ForgotPasswordActivity activity) {
            mActivity = activity;
        }

        @Override
        public String getName() {
            return "ForgotPasswordIdlingResource:" + mActivity.getLocalClassName();
        }

        @Override
        public boolean isIdleNow() {
            ProgressBar statusTextView = mActivity.mProgressBar;
            boolean idle = (statusTextView == null || statusTextView.getVisibility() == View.GONE);

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
