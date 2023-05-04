package mzc.app.view_model.page;

import mzc.app.view.components.member_list.LeftSideMemberView;

public class MemberListPageViewModel extends SplitPageViewModel {
    public MemberListPageViewModel() {
        super("Daftar Member");
    }

    @Override
    public void init() {
        super.init();
        this.setLeft(new LeftSideMemberView());
    }

}
