package application.android.com.expencestracker.Model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Field;
import java.util.Date;
import static org.junit.Assert.*;

public class ExpenseTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void ExpenseTest()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Expense exp = new Expense();
        Class<?> cl = exp.getClass();
        Field amountField = cl.getDeclaredField("amount");
        Field categoryNameField = cl.getDeclaredField("category");
        Field dateField = cl.getDeclaredField("date");
        Field expenseidField = cl.getDeclaredField("expenseid");
        amountField.setAccessible(true);
        categoryNameField.setAccessible(true);
        dateField.setAccessible(true);
        expenseidField.setAccessible(true);
        double amount = (double) amountField.get(exp);
        String category = (String) categoryNameField.get(exp);
        Date date = (Date) dateField.get(exp);
        int expenseid = (int) expenseidField.get(exp);

        assertEquals(0, amount,0.000001);
        assertEquals(null, category);
        assertEquals(null, date);
        assertEquals(0, expenseid);

        exp.setAmount(100);
        amount = (double) amountField.get(exp);
        assertEquals(100, amount,0.000001);
        assertEquals(100, exp.getAmount(),0.000001);

        exp.setCategory("food");
        category = (String) categoryNameField.get(exp);
        assertEquals("food", category);
        assertEquals("food", exp.getCategory());

        date = DateUtil.createDate("11/11/2018");
        exp.setDate(date);
        assertSame(date, exp.getDate());
        assertEquals("11/11/2018", DateUtil.dateToString(exp.getDate()));

        exp.setId(5);
        expenseid = (int) expenseidField.get(exp);
        assertEquals(5, expenseid);
        assertEquals(5, exp.getId());
    }
}