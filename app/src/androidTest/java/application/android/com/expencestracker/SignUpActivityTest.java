package application.android.com.expencestracker;

import android.support.test.rule.ActivityTestRule;
import android.widget.Button;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class SignUpActivityTest {

    @Rule
    public ActivityTestRule<SignUpActivity> rule = new ActivityTestRule<SignUpActivity>(SignUpActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void checkSignUpButton() throws Exception {
        Button btn = (Button) rule.getActivity().findViewById(R.id.button_SignUp);
        assertNotNull(btn);
    }
}