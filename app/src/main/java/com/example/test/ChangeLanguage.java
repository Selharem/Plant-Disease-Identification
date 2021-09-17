package com.example.test;

import android.app.Activity;
import android.content.res.Configuration;

import java.util.Locale;

public class ChangeLanguage {

    static String langue="en";

    public static void changeLanguage(Activity activity, String language){
        String languageToLoad  = language;
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        activity.getBaseContext().getResources().updateConfiguration(config,
                activity.getBaseContext().getResources().getDisplayMetrics());
    }
}
