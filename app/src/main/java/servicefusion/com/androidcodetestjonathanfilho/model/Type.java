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

    public String getName(String name){
        return name;
    }

}
