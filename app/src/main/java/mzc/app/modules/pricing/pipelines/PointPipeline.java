package mzc.app.modules.pricing.pipelines;

import lombok.NonNull;
import mzc.app.modules.pricing.DecimalPrice;
import mzc.app.modules.pricing.IPrice;
import mzc.app.modules.pricing.PricePipelineResult;

import java.math.BigDecimal;

public class PointPipeline implements IPricePipeline {
    final private @NonNull Integer userPoints;

    public PointPipeline(@NonNull Integer userPoints) {
        this.userPoints = userPoints;
    }

    @Override
    public @NonNull PricePipelineResult calculate(IPrice input) {
        IPrice nominal = new DecimalPrice(new BigDecimal(this.userPoints));
        IPrice total = new DecimalPrice(input.getValue().subtract(nominal.getValue()));
        return new PricePipelineResult("Poin", nominal, total);
    }
}
