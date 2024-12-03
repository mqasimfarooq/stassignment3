package test;

import loginapp.LoginApp;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

class LoginAppTest {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/softwaretesting";
    private static final String DB_USER = "Qasim";
    private static final String DB_PASSWORD = "qasim2002";

    private final LoginApp loginApp = new LoginApp();

    @Test
    void shouldLoginSuccessfullyWithValidEmailAndPassword() {
        String email = "janesmith@example.com";
        String password = "password456";
        String userName = loginApp.authenticateUser(email, password);

        assertNotNull(userName);
        assertEquals("Jane Smith", userName);
    }

    @Test
    void shouldLoginWithSpecialCharactersOrNumbersInEmail() {
        String email = "mikejohnson@example.com";
        String password = "password789";
        String userName = loginApp.authenticateUser(email, password);

        assertNotNull(userName);
        assertEquals("Mike Johnson", userName);
    }

    @Test
    void shouldFailLoginWithInvalidEmailFormat() {
        String email = "invalidemail.com";
        String password = "anyPassword";
        String userName = loginApp.authenticateUser(email, password);

        assertNull(userName);
    }

    @Test
    void shouldFailLoginWithEmptyEmail() {
        String email = "";
        String password = "password123";
        String userName = loginApp.authenticateUser(email, password);

        assertNull(userName);
    }

    @Test
    void shouldFailLoginWithValidEmailButIncorrectPassword() {
        String email = "johndoe@example.com";
        String password = "incorrectpassword";
        String userName = loginApp.authenticateUser(email, password);

        assertNull(userName);
    }

    @Test
    void shouldFailLoginWithNonExistentEmail() {
        String email = "nonexistent@example.com";
        String password = "password123";
        String userName = loginApp.authenticateUser(email, password);

        assertNull(userName);
    }

    @Test
    void shouldFailLoginWithNoPassword() {
        String email = "john@example.com";  // Email exists but password is NULL
        String password = "";               // Empty password
        String userName = loginApp.authenticateUser(email, password);

        assertNull(userName);
    }

    @Test
    void shouldFailLoginWithEmptyEmailAndValidPassword() {
        String email = "";  // Empty email
        String password = "password123";  // Valid password
        String userName = loginApp.authenticateUser(email, password);

        assertNull(userName);
    }

    @Test
    void shouldFailLoginWithValidEmailAndNullPassword() {
        String email = "john@example.com";  // Valid email but password is NULL in DB
        String password = "";               // Attempt to login with an empty password
        String userName = loginApp.authenticateUser(email, password);

        assertNull(userName);
    }

    @Test
    void shouldLoginWithSpecialCharacterInPassword() {
        String email = "janedoe2@example.com";
        String password = "p@ssw0rd!";
        String userName = loginApp.authenticateUser(email, password);

        assertNotNull(userName);
        assertEquals("Jane Doe", userName);
    }


    @Test
    void shouldFailLoginWithEmailInUppercase() {
        String email = "JANESMITH@EXAMPLE.COM";  // Uppercase email
        String password = "password456";         // Correct password
        String userName = loginApp.authenticateUser(email, password);

        assertNotNull(userName);
        assertEquals("Jane Smith", userName);
    }

    @Test
    void shouldFailLoginWithInvalidEmailDomain() {
        String email = "janesmith@invalid.com";  // Invalid email domain
        String password = "password456";          // Correct password
        String userName = loginApp.authenticateUser(email, password);

        assertNull(userName);
    }

    @Test
    void shouldFailLoginWithSQLInjection() {
        String email = "janesmith@example.com'; DROP TABLE Users; --";
        String password = "password456";
        String userName = loginApp.authenticateUser(email, password);

        assertNull(userName);
    }
}
