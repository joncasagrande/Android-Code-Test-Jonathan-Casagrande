package servicefusion.com.androidcodetestjonathanfilho.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by joncasagrande on 12/09/17.
 */
public class EmailTest {
    Email email;
    @Before
    public void setUp() throws Exception {
        email = new Email("email@email.com", Type.HOME.toString());

    }

    @Test
    public void getEmail() throws Exception {
        assertEquals("email doesnt match", email.getEmail(),"email@email.com");
    }

    @Test
    public void setEmail() throws Exception {
        email.setEmail("new_email@email.com");
        assertEquals("set email is not working", email.getEmail(),"new_email@email.com");
    }

    @Test
    public void getType() throws Exception {
        assertEquals("Should be the same", email.getTypeDescription(), Type.HOME.toString());
    }

    @Test
    public void setType() throws Exception {
        email.setTypeDescription(Type.WORK.toString());
        assertEquals("Should had setwork type", email.getTypeDescription(), Type.WORK.toString());

    }

    @Test
    public void shouldBeAValidEmail() throws  Exception{
        email.setEmail("email");
        assertEquals("email doesnt match", email.getEmail(),"email@email.com");
    }

}