package mzc.plugin_charge.view_model;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.Getter;
import mzc.app.modules.pricing.pipelines.IPricePipeline;
import mzc.app.modules.pricing.pipelines.PricePipelineType;
import mzc.app.view_model.components.cashier.PaymentSummaryViewModel;
import mzc.plugin_charge.adapter.ChargeManager;
import mzc.plugin_charge.models.Charge;
import mzc.plugin_charge.pricing.ChargePipeline;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class SubtotalViewModel extends mzc.app.view_model.components.cashier.SubtotalViewModel {
    @Getter
    protected ComboBox<String> additionalDiscount = new ComboBox<>();

    @Getter
    protected TextField discountValue = new TextField();

    @Override
    public void init() {
        super.init();

        additionalDiscount.getItems().addAll("Tidak ada", "Tetap", "Persen");
        additionalDiscount.setValue("Tidak ada");

        actionsContainer.getChildren().add(additionalDiscount);
        actionsContainer.getChildren().add(discountValue);

        this.additionalDiscount.setOnAction(e -> {
            var context = useContext(PaymentSummaryViewModel.PaymentSummaryContext.class).getValue();
            this.calculate(context.getProductItems().getValue().values().stream().toList());
        });

        this.discountValue.setOnAction(e -> {
            var context = useContext(PaymentSummaryViewModel.PaymentSummaryContext.class).getValue();
            this.calculate(context.getProductItems().getValue().values().stream().toList());
        });
    }

    @Override
    protected List<IPricePipeline> getPipelines() {
        var prev = super.getPipelines();

        var charges = ChargeManager.getAdapter().getAll();

        charges.forEach(each -> {
            prev.add(new ChargePipeline(each));
        });

        if (!Objects.equals(additionalDiscount.getValue(), "Tidak ada")) {
            PricePipelineType type;

            if (additionalDiscount.getValue().equals("Tetap")) {
                type = PricePipelineType.FIXED;
            } else {
                type = PricePipelineType.PERCENTAGE;
            }
            var charge = new Charge(type, new BigDecimal(discountValue.getText()), "additional", "Diskon Tambahan");

            prev.add(new ChargePipeline(charge));
        }

        return prev;
    }
}
