package mzc.plugin_system1.setting;

import lombok.Getter;
import mzc.app.modules.setting.BaseSetting;
import mzc.plugin_system1.model.Currency;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CurrencySetting extends BaseSetting {

    @Getter
    @NotNull
    private String defaultCurrency;

    public CurrencySetting(@NotNull String defaultCurrency) {
        super(getDefaultPath());
        this.defaultCurrency = defaultCurrency;
    }

    private static String getDefaultPath() {
        return Paths.get("./data/setting.currency").normalize().toAbsolutePath().toString();
    }

    public static CurrencySetting load() {
        CurrencySetting result;

        try {
            String path = getDefaultPath();
            FileInputStream file = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(file);

            result = (CurrencySetting) in.readObject();

            in.close();
            file.close();
        } catch (IOException | ClassNotFoundException ignored) {
            result = new CurrencySetting(
                    "Rupiah"
            );

            try {
                result.save();
            } catch (Exception exception) {
                throw new RuntimeException(exception.getMessage());
            }
        }

        return result;
    }

    public static List<Currency> getCurrencySeed() {
        List<Currency> currencies = new ArrayList<>();
        currencies.add(new Currency("Rp", "Rupiah", new BigDecimal(1)));
        currencies.add(new Currency("$", "Dollar", new BigDecimal("14740.15")));
        currencies.add(new Currency("㍐", "Yuan", new BigDecimal("2132.51")));
        return currencies;
    }
}
