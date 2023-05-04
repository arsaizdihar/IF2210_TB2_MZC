package mzc.app.view.components.member_list;

import mzc.app.annotation.ModelInject;
import mzc.app.view.components.split_view.RightSideView;
import mzc.app.view_model.components.member_list.RightSideMemberViewModel;
import mzc.app.view_model.components.product_list.RightSideProductViewModel;

@ModelInject(RightSideMemberViewModel.class)
public class RightSideMemberView extends RightSideView<RightSideMemberViewModel> {
    @Override
    public RightSideMemberViewModel getViewModel() {
        return super.getViewModel();
    }
}
