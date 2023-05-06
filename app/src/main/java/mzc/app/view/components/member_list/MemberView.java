package mzc.app.view.components.member_list;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.model.Customer;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.member_list.MemberViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(MemberViewModel.class)
public class MemberView extends BaseView<MemberViewModel> {

    public MemberView(Customer customer) {
        getViewModel().setCustomer(customer);
        getViewModel().getSetActiveButton().setText(customer.isDeactivated() ? "Activate" : "Deactivate");
    }
    @Override
    public @NotNull Node getView() { return getViewModel().getMemberBox(); }
}
