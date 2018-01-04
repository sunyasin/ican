package org.ideacreation.can;

import android.app.Application;

import org.ideacreation.can.di.DaggerDiComponent;
import org.ideacreation.can.di.DiComponent;

/**
 * Main application class
 */

public class App extends Application {

    private static DiComponent component;
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        component = DaggerDiComponent.builder().build();
    }

    public static DiComponent getInjector() {
        return component;
    }

    public static App getInstance() {
        return instance;
    }
}
