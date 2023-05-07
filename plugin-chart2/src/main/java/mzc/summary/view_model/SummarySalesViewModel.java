package mzc.summary.view_model;

import javafx.scene.chart.PieChart;
import mzc.app.model.CustomerType;
import mzc.app.utils.Tuple;
import mzc.app.view_model.base.BaseViewModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SummarySalesViewModel extends BaseViewModel {
    public List<PieChart.Data> getData() {
        List<PieChart.Data> data = new ArrayList<>();
        Map<CustomerType, BigDecimal> accumulator = new HashMap<>();

        var sales = getAdapter().getFixedBill().getAll().stream().map(fixedBill -> {
            var customer = getAdapter().getCustomer().getById(fixedBill.getCustomerId());
            final BigDecimal[] sum = {new BigDecimal(0)};

            getAdapter().getProductHistory().getByBillId(fixedBill.getId()).forEach(productHistory -> {
                sum[0] = sum[0].add(productHistory.getPrice().multiply(BigDecimal.valueOf(productHistory.getAmount())));
            });

            return new Tuple<>(customer.getType(), sum[0]);
        });

        sales.forEach(sale -> {
            if (accumulator.containsKey(sale.x)) {
                accumulator.put(sale.x, accumulator.get(sale.x).add(sale.y));
            } else {
                accumulator.put(sale.x, sale.y);
            }
        });

        for (var sale : accumulator.entrySet()) {
            data.add(new PieChart.Data(sale.getKey().toString(), sale.getValue().doubleValue()));
        }

        return data;
    }
}
