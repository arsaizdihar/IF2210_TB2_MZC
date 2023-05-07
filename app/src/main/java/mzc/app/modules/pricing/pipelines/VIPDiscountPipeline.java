package mzc.app.modules.pricing.pipelines;

import lombok.NonNull;
import mzc.app.model.ProductHistory;
import mzc.app.modules.pricing.PriceFactory;
import mzc.app.modules.pricing.PricePipelineResult;
import mzc.app.modules.pricing.price.IPrice;

import java.math.BigDecimal;

public class VIPDiscountPipeline extends PricePipeline {
    private final BigDecimal percentage = new BigDecimal("0.1");

    private PricePipelineResult lastResult;

    @Override
    public @NonNull PricePipelineResult calculate(IPrice input) {
        IPrice nominal = PriceFactory.createPriceView(input.getValue().multiply(this.percentage).multiply(new BigDecimal(-1)));
        IPrice total = PriceFactory.createPriceView(input.getValue().add(nominal.getValue()));
        this.lastResult = new PricePipelineResult("Diskon VIP", nominal, total);
        return lastResult;
    }

    @Override
    public @NonNull PricePipelineType getType() {
        return PricePipelineType.PERCENTAGE;
    }

    @Override
    public int getPriority() {
        return 20;
    }

    @Override
    public ProductHistory createHistory() {
        var history = new ProductHistory();
        history.setAmount(1);
        history.setPrice(this.lastResult.getNominal().getValue());
        history.setName("Diskon VIP");
        history.setCategory("additional");
        history.setBuyPrice(new BigDecimal(0));
        return history;
    }
}
