package mzc.app.modules.pricing;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.modules.pricing.price.IPrice;

public class PricePipelineResult {
    @Getter
    final private @NonNull String name;
    @Getter
    final private @NonNull IPrice nominal;
    @Getter
    final private @NonNull IPrice total;

    public PricePipelineResult(@NonNull String name, @NonNull IPrice nominal, @NonNull IPrice total) {
        this.name = name;
        this.nominal = nominal;
        this.total = total;
    }
}
