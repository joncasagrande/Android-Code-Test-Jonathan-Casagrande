package servicefusion.com.androidcodetestjonathanfilho;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by joncasagrande on 14/09/17.
 */

public class ServiceFusionApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        Realm.init(this);

    }
}
