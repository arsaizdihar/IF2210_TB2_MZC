package mzc.summary.view;

import javafx.scene.layout.HBox;
import lombok.Getter;
import mzc.app.view_model.base.PageViewModel;
import mzc.summary.view_model.SummaryCustomerView;
import mzc.summary.view_model.SummarySalesView;

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

        this.container.getChildren().addAll(sales.getView(), customer.getView());
    }

    @Override
    public void onTabFocus() {
        reload();
    }

    @Override
    public void onTabClose() {

    }
}
