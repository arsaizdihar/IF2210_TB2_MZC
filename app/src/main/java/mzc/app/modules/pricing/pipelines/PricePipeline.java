package mzc.app.modules.pricing.pipelines;

import lombok.NonNull;
import mzc.app.modules.pricing.PricePipelineResult;
import mzc.app.modules.pricing.price.IPrice;

public abstract class PricePipeline implements Comparable<PricePipeline> {
    abstract public @NonNull PricePipelineResult calculate(IPrice input);

    abstract public @NonNull PricePipelineType getType();

    abstract public int getPriority();

    @Override
    public int compareTo(PricePipeline pipeline) {
        return getPriority() - pipeline.getPriority();
    }
}
