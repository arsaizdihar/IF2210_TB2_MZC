package mzc.app.modules.pricing.pipelines;

import lombok.NonNull;
import mzc.app.modules.pricing.PricePipelineResult;
import mzc.app.modules.pricing.price.IPrice;

public interface IPricePipeline {
    @NonNull PricePipelineResult calculate(IPrice input);

    @NonNull PricePipelineType getType();
}
