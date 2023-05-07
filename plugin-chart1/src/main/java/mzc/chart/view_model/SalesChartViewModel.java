package mzc.chart.view_model;

import javafx.scene.chart.XYChart;
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

    @Override
    public void onTabFocus() {

    }

    @Override
    public void onTabClose() {

    }

    public XYChart.Series<String, Double> getSalesSeries(boolean isProfit) {
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

        XYChart.Series<String, Double> series = new XYChart.Series<>();

        profitGrouped.keySet().stream().sorted().forEach(key -> {
            series.getData().add(new XYChart.Data<>(key, profitGrouped.get(key).stream().map(each -> each.y.doubleValue()).reduce((double) 0, Double::sum)));
        });

        return series;
    }
}
