package mzc.app.modules.pricing;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.modules.pricing.pipelines.PricePipeline;
import mzc.app.modules.pricing.price.ItemListPrice;

import java.util.ArrayList;
import java.util.List;

public class PriceCalculator {
    @Getter
    final private @NonNull List<PricePipeline> pipelines;

    public PriceCalculator(@NonNull List<PricePipeline> pipelines) {
        this.pipelines = pipelines.stream().sorted().toList();
    }

    public List<PricePipelineResult> calculate(@NonNull ItemListPrice items) {
        List<PricePipelineResult> result = new ArrayList<>();

        PricePipelineResult current = new PricePipelineResult("Subtotal", PriceFactory.createPriceView(items), PriceFactory.createPriceView(items));
        result.add(current);

        for (var pipeline : this.pipelines) {
            current = pipeline.calculate(current.getTotal());
            result.add(current);
        }

        return result;
    }
}
