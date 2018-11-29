package application.android.com.expencestracker.Model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createDate() throws Exception{
        try {
            Date t_date = DateUtil.createDate("12-12-208");
            Assert.fail("Fail the test.");
        } catch (IllegalArgumentException ex) {

        }
        try {
            Date t_date = DateUtil.createDate("12/24/2018");
            assertNotNull(t_date);
        } catch (IllegalArgumentException ex) {
            Assert.fail("Fail the test.");
        }
    }

    @Test
    public void dateToString() {
        Date d_date = new Date(114, 10, 13);
        String s = DateUtil.dateToString(d_date);
        assertEquals(s, "11/13/2014");
    }
}