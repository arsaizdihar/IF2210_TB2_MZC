package mzc.app.modules.pricing;

import lombok.NonNull;

import java.math.BigDecimal;

public class DecimalPrice implements IPrice {
    protected @NonNull BigDecimal value;

    public DecimalPrice(@NonNull BigDecimal value) {
        this.value = value;
    }

    public DecimalPrice(@NonNull IPrice value) {
        this.value = value.getValue();
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
