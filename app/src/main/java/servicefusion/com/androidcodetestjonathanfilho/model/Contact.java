package servicefusion.com.androidcodetestjonathanfilho.model;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by joncasagrande on 12/09/17.
 */
public class Contact extends RealmObject{

    @PrimaryKey
    private long id;
    private String name;
    private String lastName;
    private Date dob;
    RealmList<Email> emails;
    RealmList<Address> addresses;
    RealmList<PhoneNumber> phoneNumbers;


    public Contact() {
    }

    public Contact(String name, String lastName, Date dob) {
        this.name = name;
        this.lastName = lastName;
        this.dob = dob;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public RealmList<Email> getEmails() {
        return emails;
    }

    public void setEmails(RealmList<Email> emails) {
        this.emails = emails;
    }

    public RealmList<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(RealmList<Address> addresses) {
        this.addresses = addresses;
    }

    public RealmList<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(RealmList<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public String toString() {
        return name + " "+ lastName;
    }
}
