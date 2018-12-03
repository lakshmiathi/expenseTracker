package application.android.com.expencestracker;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class BarFragmentTest {

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
    public void testBarFragment() throws Exception {
        onView(withId(R.id.BarChart)).perform(click());
        Fragment bar_frag = mMainActivity.getSupportFragmentManager().findFragmentByTag("BAR_FRAGMENT");
        assertTrue(bar_frag.isVisible());
        BarChart barChart = (BarChart)bar_frag.getActivity().findViewById(R.id.chart);
        assertNotNull(barChart);
        BarData BARDATA = barChart.getBarData();
        assertNotNull(BARDATA);
    }

    @Test
    public void queryYData() {
        onView(withId(R.id.BarChart)).perform(click());
        Fragment bar_frag = mMainActivity.getSupportFragmentManager().findFragmentByTag("BAR_FRAGMENT");
        ArrayList<Double> result = ((BarFragment) bar_frag).queryYData();
        assertNotNull(result);
    }

}