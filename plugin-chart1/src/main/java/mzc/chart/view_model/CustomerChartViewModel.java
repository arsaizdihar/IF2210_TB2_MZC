package mzc.chart.view_model;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import lombok.Getter;
import mzc.app.model.CustomerType;
import mzc.app.utils.Tuple;
import mzc.app.view_model.base.PageViewModel;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerChartViewModel extends PageViewModel {
    public CustomerChartViewModel() {
        super("Chart Customer");
    }

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Getter
    private StackedBarChart<String, Number> stackedBarChart;

    private boolean isInitial = true;

    @Override
    public void onTabFocus() {
        if (isInitial) {
            isInitial = false;
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
        xAxis.setLabel("Keuntungan");
        var yAxis = new NumberAxis();
        yAxis.setLabel("Banyaknya Pelanggan");

        this.stackedBarChart = new StackedBarChart<>(xAxis, yAxis);
        this.stackedBarChart.setTitle("Banyaknya Sales Berdasarkan Tipe Pelanggan");
        this.stackedBarChart.setStyle("-fx-font-weight: bold;");
        this.stackedBarChart.setAnimated(true);

        reload();
    }

    public void reload() {
        this.stackedBarChart.getData().clear();
        this.stackedBarChart.getData().addAll(this.getCustomerSeries());

        for (var s : stackedBarChart.getData()) {
            for (var d : s.getData()) {
                Tooltip.install(d.getNode(), new Tooltip(
                        d.getXValue() + "\n" +
                                "Banyaknya penjualan: " + d.getYValue()));

                //Adding class on hover
                d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));

                //Removing class on exit
                d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
            }
        }
    }

    public List<XYChart.Series<String, Number>> getCustomerSeries() {
        var customerTypes = getAdapter().getFixedBill().getAll().stream().map(fixedBill -> {
            var customer = getAdapter().getCustomer().getById(fixedBill.getCustomerId());
            return new Tuple<>(formatter.format(fixedBill.getCreatedAt()), customer.getType());
        });
        var customerGrouped = customerTypes.collect(Collectors.groupingBy(Tuple<String, CustomerType>::getX));

        var basicSeries = new XYChart.Series<String, Number>();
        var memberSeries = new XYChart.Series<String, Number>();
        var vipSeries = new XYChart.Series<String, Number>();

        customerGrouped.forEach((x, y) -> {
            y.stream().collect(Collectors.groupingBy(Tuple<String, CustomerType>::getY)).forEach((type, typeList) -> {
                switch (type) {
                    case VIP -> vipSeries.getData().add(new XYChart.Data<>(x, typeList.size()));
                    case MEMBER -> memberSeries.getData().add(new XYChart.Data<>(x, typeList.size()));
                    case BASIC -> basicSeries.getData().add(new XYChart.Data<>(x, typeList.size()));
                }
            });
        });

        basicSeries.setName("Basic");
        memberSeries.setName("Member");
        vipSeries.setName("VIP");

        return Arrays.asList(basicSeries, memberSeries, vipSeries);
    }
}
