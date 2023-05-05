package mzc.app.view_model.page;

import mzc.app.view.components.product_list.LeftSideProductView;

public class ProductListPageViewModel extends SplitPageViewModel {
    public ProductListPageViewModel() {
        super("Daftar Barang");
    }

    @Override
    public void init() {
        super.init();
        this.setLeft(new LeftSideProductView());
    }

    @Override
    public void onTabFocus() {

    }

    @Override
    public void onTabClose() {

    }
}
