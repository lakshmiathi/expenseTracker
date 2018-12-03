package application.android.com.expencestracker;

import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import application.android.com.expencestracker.DBImp.UserTableImp;

import static android.support.test.InstrumentationRegistry.getArguments;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class HomeFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mMainActivity;

    @Before
    public void setUp() throws Exception {
        mMainActivity = rule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        mMainActivity = null;
    }

    @Test
    public void onCreateView() {
        Fragment bar_frag = mMainActivity.getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT");
        assertTrue(bar_frag.isVisible());
        onView(withId(R.id.buttton_limit)).perform(click());
        onView(withId(R.id.editText_limit)).perform(clearText(), typeText("12345"));
        onView(withId(R.id.button_ok)).perform(click());
        UserTableImp user = new UserTableImp(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String limit = user.getLimit(Integer.parseInt(mMainActivity.user_id));
        assertNotNull(onView(withId(R.id.editText_limit)));
        assertEquals("12345",limit);
    }
}