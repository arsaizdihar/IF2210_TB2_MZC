package mzc.app.view.components.member_list;


import mzc.app.annotation.ModelInject;
import mzc.app.view.components.split_view.LeftSideView;
import mzc.app.view_model.components.member_list.LeftSideMemberListViewModel;

@ModelInject(LeftSideMemberListViewModel.class)
public class LeftSideMemberListView extends LeftSideView<LeftSideMemberListViewModel> {
    public LeftSideMemberListView() {
        super("Daftar Member", "Tambah Member");
    }
}
