package application.android.com.expencestracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
//import org.mockito.Mock;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.core.internal.deps.guava.base.Optional;
import android.support.test.rule.ActivityTestRule;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

//import static org.mockito.Mockito.when;


public class loginActivityTest {

    @Rule
    public ActivityTestRule<loginActivity> rule = new ActivityTestRule<loginActivity>(loginActivity.class);
    private loginActivity mLogInActivity;
    // Define the monitor to check the log in Activity.
    private Instrumentation.ActivityMonitor monitor;

    @Before
    public void setUp() throws Exception {
        mLogInActivity = rule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        mLogInActivity = null;
    }

    @Test
    public void checkSignInButtonFail() throws Exception {
        Button btn = (Button) mLogInActivity.findViewById(R.id.button_SignIn);
        assertNotNull(btn);
    }

    @Test
    public void checkSignUpTextView() throws Exception {
        TextView tv_signup = (TextView) mLogInActivity.findViewById(R.id.textView_SignUp);
        assertNotNull(tv_signup);
    }

    @Test
    public void checkSignInFailOnClick() throws Exception {
        monitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);
        onView(withId(R.id.editText_Email)).perform(clearText(), typeText("no@tom.cat"));
        onView(withId(R.id.editText_Password)).perform(clearText(), typeText("wei"));
        // Now perform the click event.
        onView(withId(R.id.button_SignIn)).perform(click());
        // Wait to get the MainActivity instance.
        Activity mMainAcitivy = getInstrumentation().waitForMonitorWithTimeout(monitor, 2000);
        assertNull(mMainAcitivy);
    }

    @Test
    public void checkSignInSucessOnClick() throws Exception {
        monitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);
        // Set the value in the view.
        onView(withId(R.id.editText_Email)).perform(clearText(), typeText("111@tom.cat"));
        onView(withId(R.id.editText_Password)).perform(clearText(), typeText("wei"));
        // Now perform the click event.
        onView(withId(R.id.button_SignIn)).perform(click());
        // Wait to get the MainActivity instance.
        Activity mMainAcitivy = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(mMainAcitivy);
    }
}
