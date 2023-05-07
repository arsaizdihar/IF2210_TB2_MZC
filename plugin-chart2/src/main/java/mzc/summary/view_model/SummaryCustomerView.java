package mzc.summary.view_model;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.summary.view.SummaryCustomerViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(SummaryCustomerViewModel.class)
public class SummaryCustomerView extends BaseView<SummaryCustomerViewModel> {
    @Override
    public @NotNull Node getView() {
        Label caption = new Label("");
        caption.setTextFill(Color.DARKGRAY);
        caption.setStyle("-fx-font: 24 arial;");

        var chart = new PieChart(FXCollections.observableList(getViewModel().getData()));
        chart.setTitle("Banyaknya Penjualan Berdasarkan Tipe Pelanggan");
        chart.getStyleClass().add("h3");

        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    e -> {
                        caption.setTranslateX(e.getX());
                        caption.setTranslateY(e.getY());
                        caption.setText(String.valueOf(data.getPieValue()));
                    });
        }

        return chart;
    }
}
