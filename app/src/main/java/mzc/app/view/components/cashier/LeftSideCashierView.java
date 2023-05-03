package mzc.app.view.components.cashier;

import mzc.app.annotation.ModelInject;
import mzc.app.view.components.split_view.LeftSideView;
import mzc.app.view_model.components.cashier.LeftSideCashierViewModel;

@ModelInject(LeftSideCashierViewModel.class)
public class LeftSideCashierView extends LeftSideView<LeftSideCashierViewModel> {
    public LeftSideCashierView() {
        super("Daftar Barang");
    }
}
