package servicefusion.com.androidcodetestjonathanfilho.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joncasagrande on 12/09/17.
 */
public class Email implements Parcelable {
    protected String email;
    protected Type type;

    public Email() {
    }

    public Email(String email, Type type) {
        this.email = email;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(isValidEmail(email))
            this.email = email;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    private boolean isValidEmail(String email){
        if (email.isEmpty() || (!email.isEmpty() &&
                email.trim()
                        .matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))) {
            return true;
        }
        return false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    }

    protected Email(Parcel in) {
        this.email = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : Type.values()[tmpType];
    }

    public static final Parcelable.Creator<Email> CREATOR = new Parcelable.Creator<Email>() {
        @Override
        public Email createFromParcel(Parcel source) {
            return new Email(source);
        }

        @Override
        public Email[] newArray(int size) {
            return new Email[size];
        }
    };
}
