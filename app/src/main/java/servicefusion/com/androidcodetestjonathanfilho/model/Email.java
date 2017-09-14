package servicefusion.com.androidcodetestjonathanfilho.model;
import io.realm.RealmObject;

/**
 * Created by joncasagrande on 12/09/17.
 */
public class Email extends RealmObject{

    private String email;
    private String typeDescription;

    public Email() {
    }

    public Email(String email, String type) {
        this.email = email;
        this.typeDescription = type;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.isEmpty() || (!email.isEmpty() &&
                email.trim()
                        .matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))) {
            this.email = email;
        }
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String type) {
        this.typeDescription = type;
    }
}
