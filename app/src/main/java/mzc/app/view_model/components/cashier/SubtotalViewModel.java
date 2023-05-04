package mzc.app.view_model.components.cashier;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.NonNull;
import mzc.app.model.CustomerType;
import mzc.app.modules.pricing.PriceCalculator;
import mzc.app.modules.pricing.PriceFactory;
import mzc.app.modules.pricing.PricePipelineResult;
import mzc.app.modules.pricing.pipelines.IPricePipeline;
import mzc.app.modules.pricing.pipelines.PointPipeline;
import mzc.app.modules.pricing.pipelines.VIPDiscountPipeline;
import mzc.app.modules.pricing.price.ItemListPrice;
import mzc.app.modules.pricing.price.ItemPrice;
import mzc.app.utils.reactive.State;
import mzc.app.view_model.base.BaseViewModel;
import mzc.app.view_model.page.CashierPageViewModel;

import java.util.ArrayList;
import java.util.List;

public class SubtotalViewModel extends BaseViewModel {
    @Getter
    protected State<Boolean> usePoints = new State<>(false);

    @Getter
    protected State<List<PricePipelineResult>> pipelineResults = new State<>(new ArrayList<>());

    @Getter
    protected VBox container = new VBox();

    @Override
    public void init() {
        super.init();

        var context = useContext(PaymentSummaryViewModel.PaymentSummaryContext.class).getValue();

        context.getProductItems().addListener((observable, prev, productItems) -> {
            this.calculate(productItems.values().stream().toList());
        });
    }

    protected List<IPricePipeline> getPipelines() {
        var context = useContext(CashierPageViewModel.CashierContext.class).getValue();
        var customer = context.getBill().getValue().getCustomer();

        List<IPricePipeline> pipelines = new ArrayList<>();

        if (this.usePoints.getValue()) {
            if (customer.getType().equals(CustomerType.BASIC)) {
                throw new RuntimeException("Basic member are not allowed to use points");
            }

            pipelines.add(new PointPipeline(customer.getPoints()));
        }

        if (customer.getType().equals(CustomerType.VIP)) {
            pipelines.add(new VIPDiscountPipeline());
        }

        return pipelines;
    }

    protected void calculate(@NonNull List<ItemPrice> productItems) {
        var calculator = new PriceCalculator(this.getPipelines());
        this.pipelineResults.setValue(calculator.calculate(new ItemListPrice(productItems)));

        this.container.getChildren().clear();

        this.getPipelineResults().getValue().forEach(result -> {
            this.container.getChildren().add(new Label(result.getName() + " " + result.getNominal()));
        });

        var lastResult = this.pipelineResults.getValue().get(this.pipelineResults.getValue().size() - 1);

        this.container.getChildren().add(new Label("Total " + PriceFactory.createPriceView(lastResult.getTotal())));
    }
}
