package mzc.summary.view_model;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import mzc.app.annotation.ModelInject;
import mzc.app.modules.pricing.PriceFactory;
import mzc.app.view.base.BaseView;
import mzc.summary.view.SummarySalesViewModel;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

@ModelInject(SummarySalesViewModel.class)
public class SummarySalesView extends BaseView<SummarySalesViewModel> {
    @Override
    public @NotNull Node getView() {
        Label caption = new Label("");
        caption.setTextFill(Color.DARKGRAY);
        caption.setStyle("-fx-font: 24 arial;");

        var chart = new PieChart(FXCollections.observableList(getViewModel().getData()));
        chart.setTitle("Penjualan Berdasarkan Tipe Pelanggan");
        chart.getStyleClass().add("h3");

        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    e -> {
                        caption.setTranslateX(e.getX());
                        caption.setTranslateY(e.getY());
                        caption.setText(PriceFactory.createPriceView(BigDecimal.valueOf(data.getPieValue())).toString());
                    });
        }
        return chart;
    }
}
