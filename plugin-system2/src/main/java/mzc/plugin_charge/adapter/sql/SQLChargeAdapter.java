package mzc.plugin_charge.adapter.sql;

import com.zaxxer.hikari.HikariDataSource;
import mzc.app.adapter.sql.ModelAdapter;
import mzc.plugin_charge.adapter.base.IChargeAdapter;
import mzc.plugin_charge.models.Charge;
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
