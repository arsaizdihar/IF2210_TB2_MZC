package mzc.app.modules.pricing.pipelines;

import lombok.NonNull;
import mzc.app.modules.pricing.PricePipelineResult;
import mzc.app.modules.pricing.price.DecimalPrice;
import mzc.app.modules.pricing.price.IPrice;
import mzc.app.modules.pricing.price.PositiveDecimalPrice;

import java.math.BigDecimal;

public class PointPipeline implements IPricePipeline {
    final private @NonNull Integer userPoints;

    public PointPipeline(@NonNull Integer userPoints) {
        this.userPoints = userPoints;
    }

    @Override
    public @NonNull PricePipelineResult calculate(IPrice input) {
        IPrice nominal = new DecimalPrice(new BigDecimal(this.userPoints * -1));
        IPrice total = new PositiveDecimalPrice(input.getValue().subtract(nominal.getValue()));
        return new PricePipelineResult("Poin", nominal, total);
    }

    @Override
    public @NonNull PricePipelineType getType() {
        return PricePipelineType.FIXED;
    }
}
