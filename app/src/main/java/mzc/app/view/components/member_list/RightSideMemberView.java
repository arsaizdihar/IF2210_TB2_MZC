package mzc.app.view.components.member_list;

import mzc.app.annotation.ModelInject;
import mzc.app.view.components.split_view.RightSideView;
import mzc.app.view_model.components.member_list.RightSideMemberViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(RightSideMemberViewModel.class)
public class RightSideMemberView extends RightSideView<RightSideMemberViewModel> {
    @Override @NotNull
    public RightSideMemberViewModel  getViewModel() {
        return super.getViewModel();
    }
}
