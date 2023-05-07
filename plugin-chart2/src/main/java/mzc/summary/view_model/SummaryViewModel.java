package mzc.summary.view_model;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import lombok.Getter;
import mzc.app.view_model.base.PageViewModel;
import mzc.summary.view.SummaryCustomerView;
import mzc.summary.view.SummarySalesView;

public class SummaryViewModel extends PageViewModel {
    public SummaryViewModel() {
        super("Summary");
    }

    @Getter
    protected HBox container = new HBox();

    @Override
    public void init() {
        super.init();
    }

    public void reload() {
        this.container.getChildren().clear();
        var sales = new SummarySalesView();
        createView(sales);
        var customer = new SummaryCustomerView();
        createView(customer);

        var spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        var spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        var spacer3 = new Region();
        HBox.setHgrow(spacer3, Priority.ALWAYS);

        this.container.getChildren().addAll(spacer1, sales.getView(), spacer2, customer.getView(), spacer3);
    }

    @Override
    public void onTabFocus() {
        reload();
    }

    @Override
    public void onTabClose() {

    }
}
