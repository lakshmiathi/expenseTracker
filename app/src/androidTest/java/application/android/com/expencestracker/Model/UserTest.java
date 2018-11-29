package application.android.com.expencestracker.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.Assert.*;

public class UserTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void UserTest()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        User user = new User();
        Class<?> cl = user.getClass();
        Field usernameField = cl.getDeclaredField("username");
        Field passwordField = cl.getDeclaredField("password");
        Field emailField = cl.getDeclaredField("email");
        Field userIdField = cl.getDeclaredField("userId");
        usernameField.setAccessible(true);
        passwordField.setAccessible(true);
        emailField.setAccessible(true);
        userIdField.setAccessible(true);
        String username = (String) usernameField.get(user);
        String password = (String) passwordField.get(user);
        String email = (String) emailField.get(user);
        int userId = (int) userIdField.get(user);

        assertEquals("", username);
        assertEquals("", password);
        assertEquals("", email);
        assertEquals(0, userId);

        user.setUsername("test");
        username = (String) usernameField.get(user);
        assertEquals("test", user.getUsername());

        user.setPassword("test1");
        password = (String) passwordField.get(user);
        assertEquals("test1", user.getPassword());

        user.setEmail("test2");
        email = (String) emailField.get(user);
        assertEquals("test2", user.getEmail());
    }
}