package mzc.app.modules.pricing.pipelines;

import lombok.NonNull;
import mzc.app.model.ProductHistory;
import mzc.app.modules.pricing.PriceFactory;
import mzc.app.modules.pricing.PricePipelineResult;
import mzc.app.modules.pricing.price.IPrice;
import mzc.app.modules.pricing.price.PositiveDecimalPrice;

import java.math.BigDecimal;

public class PointPipeline extends PricePipeline {
    final private @NonNull Integer userPoints;

    private PricePipelineResult lastResult;

    public PointPipeline(@NonNull Integer userPoints) {
        this.userPoints = userPoints;
    }

    @Override
    public @NonNull PricePipelineResult calculate(IPrice input) {
        IPrice nominal = PriceFactory.createPriceView(new BigDecimal(this.userPoints * -1));
        IPrice total = PriceFactory.createPriceView(new PositiveDecimalPrice(input.getValue().add(nominal.getValue())));
        this.lastResult = new PricePipelineResult("Poin", nominal, total);
        return lastResult;
    }

    @Override
    public @NonNull PricePipelineType getType() {
        return PricePipelineType.FIXED;
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public ProductHistory createHistory() {
        var history = new ProductHistory();
        history.setAmount(1);
        history.setName("Points");
        history.setCategory("additional");
        history.setPrice(this.lastResult.getNominal().getValue());
        history.setBuyPrice(new BigDecimal(0));

        return history;
    }
}
