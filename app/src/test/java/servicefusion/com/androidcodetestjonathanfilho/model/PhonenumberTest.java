package servicefusion.com.androidcodetestjonathanfilho.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by joncasagrande on 13/09/17.
 */
public class PhonenumberTest {
    PhoneNumber phoneNumber;
    @Before
    public void setUp() throws Exception {
        phoneNumber = new PhoneNumber();
        phoneNumber.setType(Type.WORK);
        phoneNumber.setNumber("918 244 395");
    }

    @Test
    public void getNumber() throws Exception {
        assertEquals("should be the same", phoneNumber.getNumber(), "918 244 395");
    }

    @Test
    public void setNumber() throws Exception {
        phoneNumber.setNumber("984211969");
        assertEquals("should be the same", phoneNumber.getNumber(), "984211969");
    }

    @Test
    public void getType() throws Exception {
        assertEquals("Should be the same", phoneNumber.getType(), Type.HOME);
    }

    @Test
    public void setType() throws Exception {
        phoneNumber.setType(Type.WORK);
        assertEquals("Should had setwork type", phoneNumber.getType(), Type.WORK);
    }

}