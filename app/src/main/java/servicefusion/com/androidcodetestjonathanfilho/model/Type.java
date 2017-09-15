package servicefusion.com.androidcodetestjonathanfilho.model;

/**
 * Created by joncasagrande on 12/09/17.
 */
public enum Type{
    WORK("work"),HOME("home"),PERSONAL("personal");

    private String name;

    Type() {
    }

    Type(String name) {
        this.name = name;
    }

    public String setName(String name){
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
