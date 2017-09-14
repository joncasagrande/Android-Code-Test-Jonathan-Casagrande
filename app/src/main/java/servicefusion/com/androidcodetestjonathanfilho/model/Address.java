package servicefusion.com.androidcodetestjonathanfilho.model;

import io.realm.RealmObject;

/**
 * Created by joncasagrande on 13/09/17.
 */
public class Address extends RealmObject{
    private String address;
    private String typeDescription;

    public Address() {
    }

    public Address(String address, String typeDescription) {
        this.address = address;
        this.typeDescription = typeDescription;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }
}
