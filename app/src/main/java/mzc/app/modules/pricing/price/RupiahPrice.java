package mzc.app.modules.pricing.price;

import lombok.NonNull;

import java.math.BigDecimal;

public class RupiahPrice implements IPrice {
    protected @NonNull BigDecimal value;

    public RupiahPrice(@NonNull BigDecimal value) {
        this.value = value;
    }

    public RupiahPrice(@NonNull IPrice value) {
        this.value = value.getValue();
    }

    @Override
    public @NonNull BigDecimal getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (this.value.compareTo(new BigDecimal(0)) < 0) {
            return "-Rp" + this.value.abs().toString();
        }
        return "Rp" + this.value.toString();
    }
}
