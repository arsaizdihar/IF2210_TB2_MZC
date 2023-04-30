package mzc.app.modules.pricing.pipelines;

import lombok.NonNull;
import mzc.app.modules.pricing.IPrice;
import mzc.app.modules.pricing.PricePipelineResult;

public class VIPDiscountPipeline implements IPricePipeline {

    @Override
    public @NonNull PricePipelineResult calculate(IPrice input) {
        return null;
    }
}
