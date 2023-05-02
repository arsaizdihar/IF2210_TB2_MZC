package mzc.plugin_system2.adapter;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.modules.pricing.pipelines.PricePipelineType;
import mzc.app.modules.setting.AppSetting;
import mzc.plugin_system2.ChargePlugin;
import mzc.plugin_system2.adapter.base.IChargeAdapter;
import mzc.plugin_system2.adapter.file.JSONChargeAdapter;
import mzc.plugin_system2.adapter.file.OBJChargeAdapter;
import mzc.plugin_system2.adapter.file.XMLChargeAdapter;
import mzc.plugin_system2.adapter.orm.ChargeAdapter;
import mzc.plugin_system2.models.Charge;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChargeManager {
    protected static IChargeAdapter adapter;

    @Getter
    protected static Map<String, Charge> charges;

    public static @NonNull IChargeAdapter getAdapter() {
        if (adapter == null) {
            AppSetting setting = ChargePlugin.getAppSetting();

            switch (setting.getStorageMethod()) {
                case OBJ -> adapter = new OBJChargeAdapter();
                case XML -> adapter = new XMLChargeAdapter();
                case JSON -> adapter = new JSONChargeAdapter();
                case SQLORM -> adapter = new ChargeAdapter(ChargePlugin.getSession());
                case SQLRaw -> adapter = new ChargeAdapter(ChargePlugin.getSession()); // temporary
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

    public static void loadCharges() {
        var availableCharges = adapter.getAll();
        charges = new HashMap<>();

        for (var charge : availableCharges) {
            charges.put(charge.getKey(), charge);
        }
    }
}
