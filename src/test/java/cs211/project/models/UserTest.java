package cs211.project.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
class UserTest {
    User user;

    @BeforeEach
    void init() {
        user = new User("PinkPPanther", "mingmmie", "@Ming1234");
    }

    @Test
    @DisplayName(" is username check")
    public void testIsUserName(){
        assertTrue(user.isUserName("mingmmie"));
    }

    @Test
    @DisplayName(" validate password ")
    public void testValidatePassword(){
        String expected = "@Ming1234";
        boolean actual = user.validatePassword(expected);
        assertTrue(actual);
    }

    @Test
    @DisplayName("gen register date")
    public void testRegisterDate(){
        String expected = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));;
        String actual = user.getRegisterDate();
        assertEquals(expected,actual);
    }
    

}

