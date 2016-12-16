package com.app.vpgroup.ghinhocungmemopad;

import android.app.Application;

import com.orm.SugarContext;

public class App extends Application {
    private static App instace;

    public static App getInstace() {
        return instace;
    }

    @Override
    public void onCreate() {
        SugarContext.init(this);
        instace = this;
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        SugarContext.terminate();
        super.onTerminate();
    }
}
