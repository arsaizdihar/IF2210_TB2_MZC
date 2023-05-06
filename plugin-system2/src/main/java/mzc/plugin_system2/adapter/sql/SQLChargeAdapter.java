package mzc.plugin_system2.adapter.sql;

import com.zaxxer.hikari.HikariDataSource;
import mzc.app.adapter.sql.ModelAdapter;
import mzc.plugin_system2.adapter.base.IChargeAdapter;
import mzc.plugin_system2.models.Charge;
import org.jetbrains.annotations.NotNull;

public class SQLChargeAdapter extends ModelAdapter<Charge> implements IChargeAdapter {
    public SQLChargeAdapter(@NotNull HikariDataSource ds) {
        super(ds);
    }

    @Override
    protected Class<Charge> getType() {
        return Charge.class;
    }
}
