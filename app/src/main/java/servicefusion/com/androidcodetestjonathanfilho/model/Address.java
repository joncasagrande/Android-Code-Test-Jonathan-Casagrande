package servicefusion.com.androidcodetestjonathanfilho.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joncasagrande on 13/09/17.
 */
public class Address implements Parcelable {
    protected String street;
    protected String number;
    protected String zipcode;

    public Address(String street, String number, String zipcode) {
        this.street = street;
        this.number = number;
        this.zipcode = zipcode;
    }

    public Address() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.street);
        dest.writeString(this.number);
        dest.writeString(this.zipcode);
    }

    protected Address(Parcel in) {
        this.street = in.readString();
        this.number = in.readString();
        this.zipcode = in.readString();
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
