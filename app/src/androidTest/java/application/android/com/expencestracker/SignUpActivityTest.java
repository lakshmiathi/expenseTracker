package application.android.com.expencestracker;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import application.android.com.expencestracker.DBImp.UserTableImp;
import application.android.com.expencestracker.Model.User;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class SignUpActivityTest {

    @Rule
    public ActivityTestRule<SignUpActivity> rule = new ActivityTestRule<SignUpActivity>(SignUpActivity.class, false, true);
    private SignUpActivity mSignUpActivity;
    // Delete the user after the test.
    private UserTableImp user_dao;

    @Before
    public void setUp() throws Exception {
        mSignUpActivity = rule.getActivity();
        user_dao = new UserTableImp(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    @After
    public void tearDown() throws Exception {
        mSignUpActivity = null;
    }

    @Test
    public void checkSignUpButton() throws Exception {
        Button btn = (Button) rule.getActivity().findViewById(R.id.button_SignUp);
        assertNotNull(btn);
    }

    @Test
    public void checkSignUpInformation() throws Exception {
        onView(withId(R.id.editText_SignUpName)).perform(clearText(), typeText("only_for_ui_test"));
        onView(withId(R.id.editText_SignUpEmail)).perform(clearText(), typeText("no123@tom.cat"));
        onView(withId(R.id.editText_SignUpPswd)).perform(clearText(), typeText("no123"));
        // Now perform the click event.
        onView(withId(R.id.button_SignUp)).perform(click());
        // Wait a while for the close.
        Thread.sleep(500);
        // The Activity should be destroyed once the sign up success.
        assertTrue(mSignUpActivity.isDestroyed());
        // delete the user.
        User usr = user_dao.getUser("no123@tom.cat");
        assertNotNull(usr);
        user_dao.delete(usr.getEmail());
    }

    @Test
    public void checkSignUpInformationIncorrect() throws Exception {
        onView(withId(R.id.editText_SignUpName)).perform(clearText(), typeText("only_for_ui_test"));
        onView(withId(R.id.editText_SignUpEmail)).perform(clearText(), typeText("no123"));
        onView(withId(R.id.editText_SignUpPswd)).perform(clearText(), typeText("no123"));
        // Now perform the click event.
        onView(withId(R.id.button_SignUp)).perform(click());
        // It will not close.
        Thread.sleep(500);
        // The Activity should be destroyed once the sign up success.
        assertFalse(mSignUpActivity.isDestroyed());
        // delete the user.
        User usr = user_dao.getUser("no123");
        assertNull(usr);
    }
}
