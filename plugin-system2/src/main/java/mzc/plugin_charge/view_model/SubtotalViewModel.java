package mzc.plugin_charge.view_model;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.Getter;
import mzc.app.modules.pricing.pipelines.PricePipeline;
import mzc.app.modules.pricing.pipelines.PricePipelineType;
import mzc.app.view.components.ui.FormGroupView;
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

        var discountGroup = new FormGroupView(additionalDiscount);
        discountGroup.setLabel("Diskon Tambahan");
        createView(discountGroup);

        var discountValueGroup = new FormGroupView(discountValue);
        discountValueGroup.setLabel("Nilai Diskon Tambahan");
        createView(discountValueGroup);
        discountValue.setMaxWidth(100);
        discountValue.setMinWidth(100);

        actionsContainer.getChildren().add(discountGroup.getView());
        actionsContainer.getChildren().add(discountValueGroup.getView());

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
    protected List<PricePipeline> getPipelines() {
        var prev = super.getPipelines();

        var charges = ChargeManager.getAdapter().getAll();

        charges.forEach(each -> {
            int priority;

            if (each.getIdentifier().equals("service")) {
                priority = 30;
            } else if (each.getIdentifier().equals("tax")) {
                priority = 5;
            } else {
                throw new RuntimeException("Not supported charge");
            }

            prev.add(new ChargePipeline(each, priority, true));
        });

        if (!Objects.equals(additionalDiscount.getValue(), "Tidak ada")) {
            PricePipelineType type;

            if (additionalDiscount.getValue().equals("Tetap")) {
                type = PricePipelineType.FIXED;
            } else {
                type = PricePipelineType.PERCENTAGE;
            }

            try {
                var charge = new Charge(type, (new BigDecimal(discountValue.getText())).multiply(BigDecimal.valueOf(-1)), "additional", "Diskon Tambahan");
                prev.add(new ChargePipeline(charge, 40));
            } catch (Exception ignored) {

            }

        }

        return prev;
    }
}
