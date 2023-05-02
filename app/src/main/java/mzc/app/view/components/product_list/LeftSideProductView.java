package mzc.app.view.components.product_list;

import mzc.app.annotation.ModelInject;
import mzc.app.view.components.split_view.LeftSideView;
import mzc.app.view_model.components.product_list.LeftSideProductViewModel;

@ModelInject(LeftSideProductViewModel.class)
public class LeftSideProductView extends LeftSideView<LeftSideProductViewModel> {
    public LeftSideProductView() {
        super("Daftar Barang", "Tambah Barang");
    }
}
