package mzc.app.bootstrap;

public class AppManager {
    protected static App app;

    public static App get() {
        if (app == null) {
            app = new App();
        }

        return app;
    }
}
