package mzc.plugin_charge.pricing;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.model.ProductHistory;
import mzc.app.modules.pricing.PriceFactory;
import mzc.app.modules.pricing.PricePipelineResult;
import mzc.app.modules.pricing.pipelines.PricePipeline;
import mzc.app.modules.pricing.pipelines.PricePipelineType;
import mzc.app.modules.pricing.price.IPrice;
import mzc.app.modules.pricing.price.PositiveDecimalPrice;
import mzc.plugin_charge.models.Charge;

import java.math.BigDecimal;

public class ChargePipeline extends PricePipeline {
    private final @NonNull Charge charge;

    public ChargePipeline(@NonNull Charge charge, int priority) {
        this.charge = charge;
        this.priority = priority;
        this.sumZero = false;
    }

    public ChargePipeline(@NonNull Charge charge, int priority, boolean sumZero) {
        this.charge = charge;
        this.priority = priority;
        this.sumZero = sumZero;
    }

    @Getter
    private int priority;

    private PricePipelineResult lastResult;
    private boolean sumZero;

    @Override
    public @NonNull PricePipelineResult calculate(IPrice input) {
        if (this.charge.getType() == PricePipelineType.FIXED) {
            IPrice nominal = PriceFactory.createPriceView(this.charge.getValue());
            IPrice total = PriceFactory.createPriceView(new PositiveDecimalPrice(input.getValue().add(nominal.getValue())));
            this.lastResult = new PricePipelineResult(this.charge.getName(), nominal, total);
        } else {
            IPrice nominal = PriceFactory.createPriceView(input.getValue().multiply(this.charge.getValue()));
            IPrice total = PriceFactory.createPriceView(input.getValue().add(nominal.getValue()));
            this.lastResult = new PricePipelineResult(this.charge.getName(), nominal, total);
        }

        return lastResult;
    }

    @Override
    public @NonNull PricePipelineType getType() {
        return this.charge.getType();
    }

    @Override
    public ProductHistory createHistory() {
        var history = new ProductHistory();
        history.setAmount(1);
        history.setCategory("additional");
        history.setName(charge.getName());
        history.setPrice(this.lastResult.getNominal().getValue());

        if (sumZero) {
            history.setBuyPrice(history.getBuyPrice().multiply(BigDecimal.valueOf(-1)));
        } else {
            history.setBuyPrice(new BigDecimal(0));
        }

        return history;
    }
}
