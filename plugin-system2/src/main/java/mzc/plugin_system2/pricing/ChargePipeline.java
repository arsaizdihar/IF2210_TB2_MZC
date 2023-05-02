package mzc.plugin_system2.pricing;

import lombok.NonNull;
import mzc.app.modules.pricing.PricePipelineResult;
import mzc.app.modules.pricing.pipelines.IPricePipeline;
import mzc.app.modules.pricing.pipelines.PricePipelineType;
import mzc.app.modules.pricing.price.DecimalPrice;
import mzc.app.modules.pricing.price.IPrice;
import mzc.app.modules.pricing.price.PositiveDecimalPrice;
import mzc.plugin_system2.models.Charge;

public class ChargePipeline implements IPricePipeline {
    private final @NonNull Charge charge;

    public ChargePipeline(@NonNull Charge charge) {
        this.charge = charge;
    }

    @Override
    public @NonNull PricePipelineResult calculate(IPrice input) {
        if (this.charge.getType() == PricePipelineType.FIXED) {
            IPrice nominal = new DecimalPrice(this.charge.getValue());
            IPrice total = new PositiveDecimalPrice(input.getValue().add(nominal.getValue()));
            return new PricePipelineResult(this.charge.getName(), nominal, total);
        } else {
            IPrice nominal = new DecimalPrice(input.getValue().multiply(this.charge.getValue()));
            IPrice total = new DecimalPrice(input.getValue().add(nominal.getValue()));
            return new PricePipelineResult("Diskon VIP", nominal, total);
        }
    }

    @Override
    public @NonNull PricePipelineType getType() {
        return this.charge.getType();
    }
}
