package application.android.com.expencestracker;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class StatisticsFragmentTest {
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
    public void testStatisticFragment() throws Exception {
        onView(withId(R.id.Statistics)).perform(click());
        Fragment stat_frag = mMainActivity.getSupportFragmentManager().findFragmentByTag("STAT_FRAGMENT");
        assertTrue(stat_frag.isVisible());
        PieChart pieChart = (PieChart)stat_frag.getActivity().findViewById(R.id.piechart);
        assertNotNull(pieChart);
        PieData data = pieChart.getData();
        assertNotNull(data);
    }

    @Test
    public void queryXData() {
        onView(withId(R.id.Statistics)).perform(click());
        Fragment pie_frag = mMainActivity.getSupportFragmentManager().findFragmentByTag("STAT_FRAGMENT");
        ArrayList<String> result = ((StatisticsFragment)pie_frag).queryXData();
        assertNotNull(result);
    }

}