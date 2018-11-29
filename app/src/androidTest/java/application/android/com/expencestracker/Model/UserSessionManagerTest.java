package application.android.com.expencestracker.Model;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class UserSessionManagerTest {

    Context mContext;
    UserSessionManager usrMgr;

    @Before
    public void setUp() throws Exception {
        mContext = InstrumentationRegistry.getTargetContext();
        usrMgr = new UserSessionManager(mContext);
    }

    @After
    public void tearDown() throws Exception {
        mContext = null;
    }

    @Test
    public void createUserLoginSessionFail() {
        usrMgr.createUserLoginSession("my_email", "my_passwd");
        boolean logInSuc = usrMgr.CheckLogIn();
        assertFalse(logInSuc);
    }

    @Test
    public void createUserLoginSessionSucess() {
        usrMgr.createUserLoginSession("111@tom.cat", "wei");
        boolean logInSuc = usrMgr.CheckLogIn();
        assertTrue(logInSuc);
    }

    @Test
    public void getUserDetails() {
        usrMgr.createUserLoginSession("111@tom.cat", "wei");
        HashMap<String, String> usrInfo = usrMgr.getUserDetails();
        assertEquals("111@tom.cat", usrInfo.get("USER_EMAIL"));
        assertEquals("wei", usrInfo.get("USER_PWD"));
    }

    @Test
    public void logoutUser() {
        usrMgr.createUserLoginSession("111@tom.cat", "wei");
        usrMgr.logoutUser();
        HashMap<String, String> usrInfo = usrMgr.getUserDetails();
        assertNull(usrInfo.get("USER_EMAIL"));
        assertNull(usrInfo.get("USER_NAME"));
        assertNull(usrInfo.get("USER_PWD"));
        // After log out, the user info ID should be -1, but it is string,
        assertEquals(usrInfo.get("USER_ID"), "-1");
    }
}