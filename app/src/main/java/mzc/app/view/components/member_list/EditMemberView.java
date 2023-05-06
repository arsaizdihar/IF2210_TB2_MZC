package mzc.app.view.components.member_list;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.model.Customer;
import mzc.app.view.components.split_view.RightSideView;
import mzc.app.view_model.components.member_list.EditMemberViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(EditMemberViewModel.class)
public class EditMemberView extends RightSideView<EditMemberViewModel> {
    public EditMemberView(Customer customer) {
        getViewModel().setCustomer(customer);
    }
    @Override
    public @NotNull Node getView() {
        return getViewModel().getRoot();
    }
}
