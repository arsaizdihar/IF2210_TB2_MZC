package mzc.plugin_currency.model;

import lombok.NonNull;
import mzc.app.modules.pricing.price.IPrice;
import mzc.plugin_currency.adapter.CurrencyManager;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyPrice implements IPrice {
    private final @NonNull BigDecimal defaultValue;

    private final @NonNull BigDecimal convertedValue;

    private final @NonNull Currency currency;

    public CurrencyPrice(@NonNull BigDecimal price) {
        this.currency = CurrencyManager.getDefaultCurrency();
        this.convertedValue = price.divide(this.currency.getConversion(), RoundingMode.UNNECESSARY);
        this.defaultValue = price;
    }

    public @NonNull BigDecimal getValue() {
        return defaultValue;
    }

    @Override
    public @NonNull String toString() {
        if (this.convertedValue.compareTo(new BigDecimal(0)) < 0) {
            return this.currency.getSymbol() + "-" + this.currency.getSymbol() + this.convertedValue.abs();
        }
        return this.currency.getSymbol() + this.convertedValue;
    }
}
