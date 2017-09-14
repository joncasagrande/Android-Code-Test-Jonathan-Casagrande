package servicefusion.com.androidcodetestjonathanfilho.model;
import io.realm.RealmObject;

/**
 * Created by joncasagrande on 13/09/17.
 */
public class PhoneNumber extends RealmObject{

    private String number;
    private String typeDescription;

    public PhoneNumber() {
    }

    public PhoneNumber(String number, String type) {
        this.number = number;
        this.typeDescription = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String type) {
        this.typeDescription = type;
    }

}
