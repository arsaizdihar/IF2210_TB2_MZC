package mzc.plugin_system2.adapter.orm;

import mzc.app.adapter.orm.ModelAdapter;
import mzc.plugin_system2.adapter.base.IChargeAdapter;
import mzc.plugin_system2.models.Charge;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;


public class ChargeAdapter extends ModelAdapter<Charge> implements IChargeAdapter {
    public ChargeAdapter(Session session) {
        super(session);
    }

    @Override
    protected @NotNull Class<Charge> getType() {
        return Charge.class;
    }
}
