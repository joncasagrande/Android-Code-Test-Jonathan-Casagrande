package servicefusion.com.androidcodetestjonathanfilho.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joncasagrande on 13/09/17.
 */

public class PhoneNumber implements Parcelable {
    protected String number;
    protected Type type;

    public PhoneNumber() {
    }

    public PhoneNumber(String number, Type type) {
        this.number = number;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.number);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    }

    protected PhoneNumber(Parcel in) {
        this.number = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : Type.values()[tmpType];
    }

    public static final Parcelable.Creator<PhoneNumber> CREATOR = new Parcelable.Creator<PhoneNumber>() {
        @Override
        public PhoneNumber createFromParcel(Parcel source) {
            return new PhoneNumber(source);
        }

        @Override
        public PhoneNumber[] newArray(int size) {
            return new PhoneNumber[size];
        }
    };
}
