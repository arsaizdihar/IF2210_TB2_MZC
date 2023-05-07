package mzc.app.view_model.page;

import mzc.app.view.components.product_list.LeftSideProductView;
import mzc.app.view_model.components.member_list.LeftSideMemberListViewModel;
import mzc.app.view_model.components.product_list.LeftSideProductViewModel;

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
        LeftSideProductViewModel.ReloadContext reload = getLeft().getViewModel().getContext(LeftSideProductViewModel.ReloadContext.class).getValue();
        reload.reload();
    }

    @Override
    public void onTabClose() {

    }
}
