package mzc.app.view.components.member_list;


import mzc.app.annotation.ModelInject;
import mzc.app.view.components.split_view.LeftSideView;
import mzc.app.view_model.components.member_list.LeftSideMemberViewModel;

@ModelInject(LeftSideMemberViewModel.class)
public class LeftSideMemberView extends LeftSideView<LeftSideMemberViewModel> {
    public  LeftSideMemberView() {
        super("Daftar Member", "Tambah Member");
    }
}
