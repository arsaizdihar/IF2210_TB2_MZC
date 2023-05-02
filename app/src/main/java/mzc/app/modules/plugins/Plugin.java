package mzc.app.modules.plugins;

import lombok.NonNull;
import mzc.app.bootstrap.App;
import org.hibernate.Session;

public abstract class Plugin {
    protected @NonNull String name;

    public Plugin(@NonNull String name) {
        this.name = name;
    }

    abstract public void setup(App appContext);

    abstract public void postSetup(Session session);
}
