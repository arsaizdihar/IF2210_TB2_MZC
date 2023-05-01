package mzc.app.modules.pricing.pipelines;

import lombok.NonNull;
import mzc.app.modules.pricing.PricePipelineResult;
import mzc.app.modules.pricing.price.DecimalPrice;
import mzc.app.modules.pricing.price.IPrice;

import java.math.BigDecimal;

public class VIPDiscountPipeline implements IPricePipeline {
    private final BigDecimal percentage = new BigDecimal("0.1");

    @Override
    public @NonNull PricePipelineResult calculate(IPrice input) {
        IPrice nominal = new DecimalPrice(input.getValue().multiply(this.percentage).multiply(new BigDecimal(-1)));
        IPrice total = new DecimalPrice(input.getValue().add(nominal.getValue()));
        return new PricePipelineResult("Diskon VIP", nominal, total);
    }

    @Override
    public @NonNull PricePipelineType getType() {
        return PricePipelineType.PERCENTAGE;
    }
}
