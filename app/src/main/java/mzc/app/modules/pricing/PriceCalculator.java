package mzc.app.modules.pricing;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.modules.pricing.pipelines.IPricePipeline;
import mzc.app.modules.pricing.pipelines.PricePipelineType;
import mzc.app.modules.pricing.price.ItemListPrice;

import java.util.ArrayList;
import java.util.List;

public class PriceCalculator {
    @Getter
    final private @NonNull List<IPricePipeline> fixedPipelines;

    @Getter
    final private @NonNull List<IPricePipeline> percentagePipelines;

    public PriceCalculator(@NonNull List<IPricePipeline> pipelines) {
        this.fixedPipelines = pipelines.stream().filter(pipeline -> pipeline.getType() == PricePipelineType.FIXED).toList();
        this.percentagePipelines = pipelines.stream().filter(pipeline -> pipeline.getType() == PricePipelineType.PERCENTAGE).toList();
    }

    public List<PricePipelineResult> calculate(@NonNull ItemListPrice items) {
        List<PricePipelineResult> result = new ArrayList<>();

        PricePipelineResult current = new PricePipelineResult("Subtotal", items, items);
        result.add(current);

        for (var pipeline : this.fixedPipelines) {
            current = pipeline.calculate(current.getTotal());
            result.add(current);
        }

        for (var pipeline : this.percentagePipelines) {
            current = pipeline.calculate(current.getTotal());
            result.add(current);
        }

        return result;
    }
}
