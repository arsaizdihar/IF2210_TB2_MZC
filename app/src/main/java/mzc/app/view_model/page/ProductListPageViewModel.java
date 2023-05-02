package mzc.app.view_model.page;

import mzc.app.view.components.product_list.LeftSideProductView;
import mzc.app.view.components.product_list.RightSideProductView;

public class ProductListPageViewModel extends SplitPageViewModel {
    public ProductListPageViewModel() {
        super("Daftar Barang");
    }

    @Override
    public void init() {
        super.init();
        this.setLeft(new LeftSideProductView());
        this.setRight(new RightSideProductView());
    }
}
