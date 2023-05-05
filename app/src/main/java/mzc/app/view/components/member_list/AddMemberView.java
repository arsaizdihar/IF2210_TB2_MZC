package mzc.app.view.components.member_list;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.view.components.split_view.RightSideView;
import mzc.app.view_model.components.member_list.AddMemberViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(AddMemberViewModel.class)
public class AddMemberView extends RightSideView<AddMemberViewModel> {
    @Override
    public @NotNull Node getView() {
        return getViewModel().getMain();
    }
}
