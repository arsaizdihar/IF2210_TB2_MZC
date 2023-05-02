package mzc.plugin_system1.model;

import lombok.NonNull;
import mzc.app.modules.pricing.price.IPrice;
import mzc.plugin_system1.adapter.CurrencyManager;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyPrice implements IPrice {
    private final @NonNull Currency currency;

    private final @NonNull BigDecimal value;

    public CurrencyPrice(BigDecimal price) {
        this.currency = CurrencyManager.getDefaultCurrency();

        this.value = price.divide(this.currency.getConversion(), RoundingMode.HALF_EVEN);
    }

    @Override
    public @NonNull BigDecimal getValue() {
        return value;
    }

    @Override
    public @NonNull String toString() {
        if (this.value.compareTo(new BigDecimal(0)) < 0) {
            return "-" + this.currency.getSymbol() + this.value.abs().toString();
        }
        return this.currency.getSymbol() + this.value.toString();
    }
}
