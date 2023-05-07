package mzc.chart.view_model;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import lombok.Getter;
import mzc.app.utils.Tuple;
import mzc.app.view_model.base.PageViewModel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

public class SalesChartViewModel extends PageViewModel {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public SalesChartViewModel() {
        super("Grafik Penjualan");
    }

    @Getter
    private LineChart<String, Number> lineChart;

    private boolean initial = true;

    @Override
    public void onTabFocus() {
        if (initial) {
            initial = false;
        } else {
            reload();
        }
    }

    @Override
    public void onTabClose() {

    }

    @Override
    public void init() {
        super.init();

        var xAxis = new CategoryAxis();
        xAxis.setLabel("Tanggal");
        var yAxis = new NumberAxis();
        yAxis.setLabel("Pemasukan/Keuntungan (Rupiah)");
        this.lineChart = new LineChart<>(xAxis, yAxis);

        reload();
    }

    public void reload() {
        lineChart.getData().clear();
        lineChart.getData().add(getSalesSeries(true));
        lineChart.getData().add(getSalesSeries(false));

        for (var s : lineChart.getData()) {
            for (var d : s.getData()) {
                Tooltip.install(d.getNode(), new Tooltip(
                        d.getXValue() + "\n" +
                                "Total profit: " + d.getYValue()));

                //Adding class on hover
                d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));

                //Removing class on exit
                d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
            }
        }
    }

    public XYChart.Series<String, Number> getSalesSeries(boolean isProfit) {
        var profit = getAdapter().getFixedBill().getAll().stream().map(fixedBill -> {
            var histories = getAdapter().getProductHistory().getByBillId(fixedBill.getId());

            Tuple<String, BigDecimal> sale = new Tuple<>(formatter.format(fixedBill.getCreatedAt()), new BigDecimal(0));

            histories.forEach(productHistory -> {
                if (isProfit) {
                    sale.y = sale.y.add(productHistory.getPrice()).subtract(productHistory.getBuyPrice()).multiply(BigDecimal.valueOf(productHistory.getAmount()));
                } else {
                    sale.y = sale.y.add(productHistory.getPrice()).multiply(BigDecimal.valueOf(productHistory.getAmount()));
                }
            });

            return sale;
        });

        var profitGrouped = profit.collect(Collectors.groupingBy(Tuple<String, BigDecimal>::getX));

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        if (isProfit) {
            series.setName("Keuntungan");
        } else {
            series.setName("Pemasukan");
        }

        profitGrouped.keySet().stream().sorted().forEach(key -> series.getData().add(new XYChart.Data<>(key, profitGrouped.get(key).stream().map(each -> each.y.doubleValue()).reduce((double) 0, Double::sum))));

        return series;
    }
}
