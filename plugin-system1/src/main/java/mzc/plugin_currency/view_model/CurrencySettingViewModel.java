package mzc.plugin_currency.view_model;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.utils.reactive.State;
import mzc.app.view.components.ui.TextInputView;
import mzc.app.view_model.components.settings.SettingsTabViewModel;
import mzc.plugin_currency.adapter.CurrencyManager;
import mzc.plugin_currency.model.Currency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CurrencySettingViewModel extends SettingsTabViewModel {
    public CurrencySettingViewModel() {
        super();
    }

    public State<List<Currency>> currencies = new State<>(new ArrayList<>());

    @Getter
    public VBox currenciesView = new VBox();

    @Getter
    public ComboBox<Currency> currencySelector = new ComboBox<>();

    @Override
    public void init() {
        super.init();
        this.getSaveButtonL().setVisible(false);

        resetCurrencies();
        reloadCurrenciesView();

        this.currencies.addListener((observableValue, prev, next) -> {
            reloadCurrenciesView();
            this.currencySelector.getItems().clear();
            this.currencySelector.getItems().addAll(next);

            this.currencySelector.setValue(next.stream().filter(Currency::isDefaultCurrency).findFirst().get());
        });

        this.currencySelector.getItems().addAll(this.currencies.getValue());
        this.currencySelector.setValue(this.currencies.getValue().stream().filter(Currency::isDefaultCurrency).findFirst().get());

        this.getSaveButtonR().setOnAction(e -> {
            var prev = this.currencies.getValue().stream().filter(Currency::isDefaultCurrency).findFirst();
            var adapter = CurrencyManager.getAdapter();

            if (prev.isEmpty()) {
                this.currencySelector.getValue().setDefaultCurrency(true);
                adapter.persist(this.currencySelector.getValue());
            } else {
                prev.get().setDefaultCurrency(false);
                adapter.persist(prev.get());
                this.currencySelector.getValue().setDefaultCurrency(true);
                adapter.persist(this.currencySelector.getValue());
            }

            resetCurrencies();
        });
    }

    public void reloadCurrenciesView() {
        this.currenciesView.getChildren().clear();
        this.currenciesView.getChildren().addAll(this.currencies.getValue().stream().map(this::createCurrencyView).toList());
        this.currenciesView.getChildren().add(createNewCurrencyView());
    }

    public Node createNewCurrencyView() {
        var symbol = new TextInputView("Simbol", false);
        symbol.getViewModel().getTextField().setMaxWidth(80);
        createView(symbol);

        var name = new TextInputView("Nama", false);
        name.getViewModel().getTextField().setMaxWidth(80);
        createView(name);

        var convertion = new TextInputView("Nilai rupiah per satuan", false);
        convertion.getViewModel().getTextField().setMaxWidth(100);
        createView(convertion);

        var add = new Button();
        add.setText("Tambah");
        add.getStyleClass().addAll("btn", "btn-primary");
        add.setOnAction(e -> {
            var adapter = CurrencyManager.getAdapter();
            var newCurrency = new Currency(symbol.getViewModel().getTextField().getText(),
                    name.getViewModel().getTextField().getText(),
                    new BigDecimal(convertion.getViewModel().getTextField().getText()));

            adapter.persist(newCurrency);
            resetCurrencies();
        });

        var container = new HBox();
//        container.setPadding(new Insets(0, 2, 2, 0));
        var addBox = new VBox(add);
        addBox.setAlignment(Pos.BOTTOM_LEFT);
        container.getChildren().addAll(new VBox(symbol.getView()), new VBox(name.getView()), new VBox(convertion.getView()), addBox);

        return container;
    }

    public Node createCurrencyView(Currency currency) {
        var symbol = new TextInputView("Simbol", false);
        symbol.getViewModel().getTextField().setMaxWidth(80);
        symbol.getViewModel().getTextField().setText(currency.getSymbol());
        symbol.getViewModel().getTextField().setDisable(true);
        symbol.getViewModel().getTextField().getStyleClass().add("normal-disabled");
        createView(symbol);

        var name = new TextInputView("Nama", false);
        name.getViewModel().getTextField().setMaxWidth(80);
        name.getViewModel().getTextField().setText(currency.getName());
        name.getViewModel().getTextField().setDisable(true);
        symbol.getViewModel().getTextField().getStyleClass().add("normal-disabled");
        createView(name);

        var convertion = new TextInputView("Nilai satuan (Rp)", false);
        convertion.getViewModel().getTextField().setMaxWidth(100);
        convertion.getViewModel().getTextField().setText(currency.getConversion().toString());
        convertion.getViewModel().getTextField().setDisable(true);
        symbol.getViewModel().getTextField().getStyleClass().add("normal-disabled");
        createView(convertion);

        var delete = new Button();
        delete.setText("Hapus");
        delete.getStyleClass().addAll("btn", "btn-danger");
        delete.setOnAction(e -> {
            var adapter = CurrencyManager.getAdapter();

            if (currency.isDefaultCurrency()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Validasi Gagal");
                alert.setContentText("Currency yang dihapus tidak boleh currency default");

                alert.showAndWait();
            } else {
                adapter.delete(currency);
                resetCurrencies();
            }
        });

        var container = new HBox();
//        container.setPadding(new Insets(0, 2, 2, 0));
        var deleteBox = new VBox(delete);
        deleteBox.setAlignment(Pos.BOTTOM_LEFT);
        container.getChildren().addAll(new VBox(symbol.getView()), new VBox(name.getView()), new VBox(convertion.getView()), deleteBox);

        return container;
    }

    public void resetCurrencies() {
        var adapter = CurrencyManager.getAdapter();
        this.currencies.getValue().clear();
        this.currencies.getValue().addAll(adapter.getAll().stream().toList());
        this.currencies.forceUpdate();
    }
}
