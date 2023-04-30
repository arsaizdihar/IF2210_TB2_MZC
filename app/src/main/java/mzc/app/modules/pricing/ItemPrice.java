package mzc.app.modules.pricing;

import lombok.NonNull;

import java.math.BigDecimal;

public class ItemPrice implements IPrice {
    private final @NonNull Integer quantity;
    private final @NonNull IPrice item;

    public ItemPrice(@NonNull Integer quantity, @NonNull IPrice item) {
        this.quantity = quantity;
        this.item = item;
    }

    @Override
    public @NonNull BigDecimal getValue() {
        return this.item.getValue().multiply(new BigDecimal(this.quantity));
    }

    @Override
    public String toString() {
        return this.quantity + " x " + this.item.toString();
    }
}
