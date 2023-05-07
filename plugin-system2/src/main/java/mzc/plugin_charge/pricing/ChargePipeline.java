package mzc.plugin_charge.pricing;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.modules.pricing.PriceFactory;
import mzc.app.modules.pricing.PricePipelineResult;
import mzc.app.modules.pricing.pipelines.PricePipeline;
import mzc.app.modules.pricing.pipelines.PricePipelineType;
import mzc.app.modules.pricing.price.IPrice;
import mzc.app.modules.pricing.price.PositiveDecimalPrice;
import mzc.plugin_charge.models.Charge;

public class ChargePipeline extends PricePipeline {
    private final @NonNull Charge charge;

    public ChargePipeline(@NonNull Charge charge, int priority) {
        this.charge = charge;
        this.priority = priority;
    }

    @Getter
    private int priority;

    @Override
    public @NonNull PricePipelineResult calculate(IPrice input) {
        if (this.charge.getType() == PricePipelineType.FIXED) {
            IPrice nominal = PriceFactory.createPriceView(this.charge.getValue());
            IPrice total = PriceFactory.createPriceView(new PositiveDecimalPrice(input.getValue().add(nominal.getValue())));
            return new PricePipelineResult(this.charge.getName(), nominal, total);
        } else {
            IPrice nominal = PriceFactory.createPriceView(input.getValue().multiply(this.charge.getValue()));
            IPrice total = PriceFactory.createPriceView(input.getValue().add(nominal.getValue()));
            return new PricePipelineResult(this.charge.getName(), nominal, total);
        }
    }

    @Override
    public @NonNull PricePipelineType getType() {
        return this.charge.getType();
    }
}
