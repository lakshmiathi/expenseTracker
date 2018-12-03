package application.android.com.expencestracker;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mMainActivity;
    // Define the monitor to check the other stuff.
    private Instrumentation.ActivityMonitor monitor;

    @Before
    public void setUp() throws Exception {
        mMainActivity = rule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        mMainActivity = null;
    }

    @Test
    public void startExpenseFragement() throws Exception {
        onView(withId(R.id.Expenses)).perform(click());
        Fragment exp_frag = mMainActivity.getSupportFragmentManager().findFragmentByTag("EXPENSE_FRAGMENT");
        assertTrue(exp_frag.isVisible());
    }

    @Test
    public void addNewExpense() throws Exception {
        onView(withId(R.id.Expenses)).perform(click());
        Fragment exp_frag = mMainActivity.getSupportFragmentManager().findFragmentByTag("EXPENSE_FRAGMENT");
        assertTrue(exp_frag.isVisible());

        // perform the click button to add a new expense.
        onView(withId(R.id.fab_Addexpens)).perform(click());

//        onView(withId(R.id.text_Date)).perform(clearText(), typeText("11/03/2018"));

//        onView(withId(R.id.text_Date)).perform(typeText("11/03/2018"));

        // click the spinner to select the category
        onView(withId(R.id.spinner_Category)).perform(click());

//        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());

//        onView(withId(R.id.text_Amount)).perform(clearText(), typeText("110"));


    }




}
