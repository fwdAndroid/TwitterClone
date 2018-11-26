package com.example.fawad.twitterclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("oGyVhBJKhGpHXnW2LHXr2xqOTud6XL0veUoe8sjQ")
                // if desired
                .clientKey("kkqKZUoHeQABIrrd3L4mnxMJZoRi8o7jaWzxkcq6")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
