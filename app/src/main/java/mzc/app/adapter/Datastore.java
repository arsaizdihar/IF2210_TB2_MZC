package mzc.app.adapter;

import org.jetbrains.annotations.NotNull;

public class Datastore {
    private static AdapterManager manager = null;

    public static void initManager() {
        if (manager == null) {
            manager = new AdapterManager();
        }
    }

    public static @NotNull AdapterManager getManager() {
        if (manager == null) {
            throw new RuntimeException("Adapter was not set");
        }

        return manager;
    }
}
