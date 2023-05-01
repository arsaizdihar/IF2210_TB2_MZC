package mzc.app.modules.pricing.price;

import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;

public class ItemListPrice implements IPrice {
    private final @NonNull List<ItemPrice> items;

    public ItemListPrice(@NonNull List<ItemPrice> items) {
        this.items = items;
    }

    @Override
    public @NonNull BigDecimal getValue() {
        return items.stream().map(ItemPrice::getValue).reduce(BigDecimal.valueOf(0), (BigDecimal::add));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (var item : items) {
            result.append(item.toString()).append("\n");
        }

        return result.toString();
    }
}
