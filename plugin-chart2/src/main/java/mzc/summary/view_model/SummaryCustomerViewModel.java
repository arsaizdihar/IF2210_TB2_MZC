package mzc.summary.view_model;

import javafx.scene.chart.PieChart;
import mzc.app.model.CustomerType;
import mzc.app.view_model.base.BaseViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SummaryCustomerViewModel extends BaseViewModel {
    public List<PieChart.Data> getData() {
        List<PieChart.Data> data = new ArrayList<>();
        Map<CustomerType, Integer> accumulator = new HashMap<>();

        var customerTypes = getAdapter().getFixedBill().getAll().stream().map(fixedBill -> getAdapter().getCustomer().getById(fixedBill.getCustomerId()).getType());

        customerTypes.forEach(type -> {
            if (accumulator.containsKey(type)) {
                accumulator.put(type, accumulator.get(type) + 1);
            } else {
                accumulator.put(type, 1);
            }
        });

        for (var sale : accumulator.entrySet()) {
            data.add(new PieChart.Data(sale.getKey().toString(), sale.getValue().doubleValue()));
        }

        return data;
    }
}
