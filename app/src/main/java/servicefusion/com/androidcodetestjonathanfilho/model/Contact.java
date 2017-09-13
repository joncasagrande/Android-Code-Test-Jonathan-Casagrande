package servicefusion.com.androidcodetestjonathanfilho.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by joncasagrande on 12/09/17.
 */

public class Contact implements Parcelable {

    private String name;
    private String lastName;
    private Date dob;
    private List<Email> emails;
    private List<Address> addresses;
    private List<PhoneNumber> phoneNumbers;


    public Contact() {
    }

    public Contact(String name, String lastName, Date dob) {
        this.name = name;
        this.lastName = lastName;
        this.dob = dob;
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

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.lastName);
        dest.writeLong(this.dob != null ? this.dob.getTime() : -1);
        dest.writeList(this.emails);
        dest.writeList(this.addresses);
        dest.writeList(this.phoneNumbers);
    }

    protected Contact(Parcel in) {
        this.name = in.readString();
        this.lastName = in.readString();
        long tmpDob = in.readLong();
        this.dob = tmpDob == -1 ? null : new Date(tmpDob);
        this.emails = new ArrayList<Email>();
        in.readList(this.emails, Email.class.getClassLoader());
        this.addresses = new ArrayList<Address>();
        in.readList(this.addresses, Address.class.getClassLoader());
        this.phoneNumbers = new ArrayList<PhoneNumber>();
        in.readList(this.phoneNumbers, PhoneNumber.class.getClassLoader());
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
