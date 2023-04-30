package mzc.app.modules.pricing.pipelines;

import lombok.NonNull;
import mzc.app.modules.pricing.IPrice;
import mzc.app.modules.pricing.PricePipelineResult;

public interface IPricePipeline {
    @NonNull PricePipelineResult calculate(IPrice input);
}
