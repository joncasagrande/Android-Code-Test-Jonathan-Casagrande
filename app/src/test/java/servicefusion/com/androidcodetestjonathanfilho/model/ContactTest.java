package servicefusion.com.androidcodetestjonathanfilho.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by joncasagrande on 12/09/17.
 */
public class ContactTest {
    Contact contact;
    @Before
    public void setUp() throws Exception {
        contact = new Contact();
    }

    @Test
    public void shouldHaveAName(){
        contact.setName("Name1");
        assertEquals("Must have a name!", contact.getName(), "Name1");
    }

    @Test
    public void mustHaveAName(){
        contact.setName("");
        assertFalse("Must have a name!", !contact.getName().isEmpty());
    }


    @Test
    public void canHaveALastName(){
        contact.setLastName("LastName");
        assertEquals("Must have a name!", contact.getLastName(), "LastName");
    }

    @Test
    public void lastNameCanBeEmpty(){
        contact.setLastName("");
        assertTrue("Last name can be empty", contact.getLastName().isEmpty());
    }
    @Test
    public void lastNameCanBeNull(){
        contact.setLastName(null);
        assertNull("Last name can be null", contact.getLastName());
    }

    @Test
    public void canHaveBirthDate(){
        Date dob = Calendar.getInstance().getTime();
        contact.setDob(dob);
        assertEquals("Should exists dob and should be equals", dob,contact.getDob());
    }

   /* @Test
    public void canHaveEmail(){
        List<Email> emails = Arrays.asList( new Email("work@email.com", Type.WORK.toString()));
        contact.setEmails(emails);
        assertNotNull(contact.getEmails());
        assertEquals("Should exists email and should be equals", emails.get(0),contact.getEmails().get(0));
    }

    @Test
    public void canHavePhoneNumbers(){
        List<PhoneNumber> phoneNumbers = Arrays.asList( new PhoneNumber("918 244 395", Type.WORK.toString()));
        contact.setPhoneNumbers(phoneNumbers);
        assertNotNull(contact.getPhoneNumbers());
        assertEquals("Should exists phone and should be equals", phoneNumbers.get(0),contact.getPhoneNumbers().get(0));
    }

    @Test
    public void canHaveAddress(){
        *//*List<Address> emails = Arrays.asList( new Email("work@email.com", Type.WORK));
        contact.setEmails(emails);
        assertNotNull(contact.getEmails());
        assertEquals("Should exists dob and should be equals", emails.get(0),contact.getEmails().get(0));*//*
    }
*/


}