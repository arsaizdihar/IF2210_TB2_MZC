package mzc.summary.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.summary.view_model.SummaryCustomerViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(SummaryCustomerViewModel.class)
public class SummaryCustomerView extends BaseView<SummaryCustomerViewModel> {
    @Override
    public @NotNull Node getView() {
        Label caption = new Label("");
        caption.setTextFill(Color.DARKGRAY);
        caption.setStyle("-fx-font: 20px;");

        var chart = new PieChart(FXCollections.observableList(getViewModel().getData()));
        chart.setTitle("Banyaknya Penjualan");
        chart.getStyleClass().add("h5");
        chart.setStyle("-fx-font-weight: bold;");

        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    e -> {
                        caption.setTranslateX(e.getScreenX());
                        caption.setTranslateY(e.getScreenY());
                        caption.setText(String.valueOf(data.getPieValue()));
                        System.out.println("Event on pie chart");
                        caption.setTranslateZ(1000);
                    });
        }

        chart.setPadding(new Insets(50));
        return chart;
    }
}
