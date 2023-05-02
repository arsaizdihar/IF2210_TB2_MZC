package mzc.app.modules.plugins;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.bootstrap.App;
import org.hibernate.Session;

public abstract class Plugin {
    @Getter
    protected @NonNull String name;

    public Plugin(@NonNull String name) {
        this.name = name;
    }

    abstract public void setup(App appContext);

    abstract public void postSetup(Session session);
}
