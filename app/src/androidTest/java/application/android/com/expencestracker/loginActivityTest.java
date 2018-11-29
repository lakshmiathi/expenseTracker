package application.android.com.expencestracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import android.support.test.rule.ActivityTestRule;
import android.widget.Button;
import android.widget.TextView;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;


public class loginActivityTest {

    @Rule
    public ActivityTestRule<loginActivity> rule = new ActivityTestRule<loginActivity>(loginActivity.class);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void checkSignInButton() throws Exception {
        Button btn = (Button) rule.getActivity().findViewById(R.id.button_SignIn);
        assertNotNull(btn);
    }

    @Test
    public void checkSignUpTextView() throws Exception {
        TextView tv_signup = (TextView) rule.getActivity().findViewById(R.id.textView_SignUp);
        assertNotNull(tv_signup);
    }

    @Test
    public void checkSignInOnClick() throws Exception {
        onView(withId(R.id.button_SignIn)).perform(click());
    }


}