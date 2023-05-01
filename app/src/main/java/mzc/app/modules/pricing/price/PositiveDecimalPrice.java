package mzc.app.modules.pricing.price;

import lombok.NonNull;

import java.math.BigDecimal;

public class PositiveDecimalPrice implements IPrice {
    protected @NonNull BigDecimal value;

    public PositiveDecimalPrice(@NonNull BigDecimal value) {
        if (value.compareTo(new BigDecimal(0)) >= 0) {
            this.value = value;
        } else {
            this.value = new BigDecimal(0);
        }
    }

    @Override
    public @NonNull BigDecimal getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
