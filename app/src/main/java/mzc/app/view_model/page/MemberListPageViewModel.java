package mzc.app.view_model.page;

import mzc.app.modules.task.CleanRedundantCustomer;
import mzc.app.view.components.member_list.LeftSideMemberListView;
import mzc.app.view_model.components.member_list.LeftSideMemberListViewModel;

public class MemberListPageViewModel extends SplitPageViewModel {
    public MemberListPageViewModel() {
        super("Daftar Member");
    }

    @Override
    public void init() {
        super.init();
        this.setLeft(new LeftSideMemberListView());
    }

    @Override
    public void onTabFocus() {
        new CleanRedundantCustomer(getAdapter()).run();
        LeftSideMemberListViewModel.ReloadContext reload = getLeft().getViewModel().getContext(LeftSideMemberListViewModel.ReloadContext.class).getValue();
        reload.reload();
    }

    @Override
    public void onTabClose() {
    }
}
