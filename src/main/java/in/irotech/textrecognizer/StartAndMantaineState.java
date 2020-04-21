package in.irotech.textrecognizer;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class StartAndMantaineState extends Application {

    //commen string name to use as title to pass the data in intents
    public static final String RESULT_TEXT="RESULT_TEXT";

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
