package application.android.com.expencestracker.DBImp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.android.com.expencestracker.Model.User;

import static org.junit.Assert.*;

public class UserTableImpTest {
    private Context context;
    private UserTableImp user_dao;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        user_dao = new UserTableImp(context);
    }

    @After
    public void tearDown() throws Exception {
        context = null;
        user_dao = null;
    }

    @Test
    public void add() {
        User usr = new User("test", "test", "test@test.com",true);
        int before = user_dao.getAmount();
        user_dao.add(usr);
        assertEquals(before+1, user_dao.getAmount());
        user_dao.delete("test@test.com");
    }

    @Test
    public void getUser() {
        User usr = new User("test", "test", "test@test.com",true);
        user_dao.add(usr);
        usr = user_dao.getUser("test@test.com");
        assertEquals("test", usr.getPassword());
        user_dao.delete("test@test.com");
    }
}