package servicefusion.com.androidcodetestjonathanfilho.model;

import io.realm.RealmObject;

/**
 * Created by joncasagrande on 13/09/17.
 */
public class Address extends RealmObject{
    private String street;
    private String number;
    private String zipcode;
    private String typeDescription;

    public Address( String street, String number, String zipcode, String typeDescription) {
        this.street = street;
        this.number = number;
        this.zipcode = zipcode;
        this.typeDescription = typeDescription;
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

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }
}
