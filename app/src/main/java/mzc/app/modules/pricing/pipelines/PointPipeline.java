package mzc.app.modules.pricing.pipelines;

import lombok.NonNull;
import mzc.app.modules.pricing.PriceFactory;
import mzc.app.modules.pricing.PricePipelineResult;
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
        IPrice nominal = PriceFactory.createPriceView(new BigDecimal(this.userPoints * -1));
        IPrice total = PriceFactory.createPriceView(new PositiveDecimalPrice(input.getValue().add(nominal.getValue())));
        return new PricePipelineResult("Poin", nominal, total);
    }

    @Override
    public @NonNull PricePipelineType getType() {
        return PricePipelineType.FIXED;
    }
}
