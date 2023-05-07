package mzc.plugin_charge.view_model;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.modules.pricing.pipelines.PricePipelineType;
import mzc.app.utils.reactive.State;
import mzc.app.view.components.ui.FormGroupView;
import mzc.app.view.components.ui.TextInputView;
import mzc.app.view_model.components.settings.SettingsTabViewModel;
import mzc.plugin_charge.adapter.ChargeManager;

import java.math.BigDecimal;

public class ChargeSettingViewModel extends SettingsTabViewModel {
    @Getter
    public VBox chargesView = new VBox();

    public State<String> taxValue = new State<>("");

    public State<PricePipelineType> taxType = new State<>(PricePipelineType.FIXED);
    public State<String> serviceFeeValue = new State<>("");
    public State<PricePipelineType> serviceFeeType = new State<>(PricePipelineType.FIXED);

    @Override
    public void init() {
        super.init();
        this.chargesView.getChildren().addAll(createTaxInput(), createServiceFeeInput());

        reloadCharge();

        this.getSaveButtonL().setOnAction(e -> {
            var adapter = ChargeManager.getAdapter();
            var charges = adapter.getAll();

            var serviceFee = charges.stream().filter(charge -> charge.getIdentifier().equals("service")).findFirst();
            var tax = charges.stream().filter(charge -> charge.getIdentifier().equals("tax")).findFirst();

            if (tax.isEmpty() || serviceFee.isEmpty()) {
                throw new RuntimeException("Tax or service charge not found");
            }

            var serviceFeeModel = serviceFee.get();
            var taxModel = tax.get();

            serviceFeeModel.setValue(new BigDecimal(serviceFeeValue.getValue()));
            serviceFeeModel.setType(serviceFeeType.getValue());

            taxModel.setType(taxType.getValue());
            taxModel.setValue(new BigDecimal(taxValue.getValue()));

            adapter.persist(serviceFeeModel);
            adapter.persist(taxModel);

            reloadCharge();
        });
    }

    public void reloadCharge() {
        var charges = ChargeManager.getAdapter().getAll();

        var serviceFee = charges.stream().filter(charge -> charge.getIdentifier().equals("service")).findFirst();
        var tax = charges.stream().filter(charge -> charge.getIdentifier().equals("tax")).findFirst();

        if (tax.isEmpty() || serviceFee.isEmpty()) {
            throw new RuntimeException("Tax or service charge not found");
        }

        taxType.setValue(tax.get().getType());
        taxValue.setValue(tax.get().getValue().toString());

        serviceFeeType.setValue(serviceFee.get().getType());
        serviceFeeValue.setValue(serviceFee.get().getValue().toString());
    }


    public Node createServiceFeeInput() {
        var typeSelector = new ComboBox<PricePipelineType>();
        typeSelector.getItems().addAll(PricePipelineType.FIXED, PricePipelineType.PERCENTAGE);
        this.serviceFeeType.bindBidirectional(typeSelector.valueProperty());

        var typeGroup = new FormGroupView(typeSelector);
        typeGroup.setLabel("Biaya Layanan");
        createView(typeGroup);

        var value = new TextInputView("Nilai", true);
        createView(value);

        this.serviceFeeValue.bindBidirectional(value.getViewModel().getTextField().textProperty());

        var container = new HBox();
        container.getChildren().addAll(typeGroup.getView(), value.getView());
        return container;
    }

    public Node createTaxInput() {
        var typeSelector = new ComboBox<PricePipelineType>();
        typeSelector.getItems().addAll(PricePipelineType.FIXED, PricePipelineType.PERCENTAGE);
        this.taxType.bindBidirectional(typeSelector.valueProperty());

        var typeGroup = new FormGroupView(typeSelector);
        typeGroup.setLabel("Pajak");
        createView(typeGroup);

        var value = new TextInputView("Nilai", true);
        createView(value);

        this.taxValue.bindBidirectional(value.getViewModel().getTextField().textProperty());

        var container = new HBox();
        container.getChildren().addAll(typeGroup.getView(), value.getView());
        return container;
    }

}
