package mzc.plugin_charge.adapter;

import lombok.NonNull;
import mzc.app.adapter.sql.ConnectionManager;
import mzc.app.modules.pricing.pipelines.PricePipelineType;
import mzc.app.modules.setting.AppSetting;
import mzc.plugin_charge.ChargePlugin;
import mzc.plugin_charge.adapter.base.IChargeAdapter;
import mzc.plugin_charge.adapter.file.JSONChargeAdapter;
import mzc.plugin_charge.adapter.file.OBJChargeAdapter;
import mzc.plugin_charge.adapter.file.XMLChargeAdapter;
import mzc.plugin_charge.adapter.sql.SQLChargeAdapter;
import mzc.plugin_charge.models.Charge;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ChargeManager {
    protected static IChargeAdapter adapter;

    public static @NonNull IChargeAdapter getAdapter() {
        if (adapter == null) {
            AppSetting setting = ChargePlugin.getAppSetting();

            switch (setting.getStorageMethod()) {
                case OBJ -> adapter = new OBJChargeAdapter();
                case XML -> adapter = new XMLChargeAdapter();
                case SQLRaw -> adapter = new SQLChargeAdapter(ConnectionManager.getDatastore());
                default -> adapter = new JSONChargeAdapter();
            }
        }

        return adapter;
    }

    public static List<Charge> getChargeSeed() {
        List<Charge> charges = new ArrayList<>();
        charges.add(new Charge(PricePipelineType.FIXED, new BigDecimal(6500), "service", "Service Fee"));
        charges.add(new Charge(PricePipelineType.PERCENTAGE, new BigDecimal("0.1"), "tax", "Tax"));
        return charges;
    }
}
