package mzc.app.view_model.page;

import lombok.Getter;
import mzc.app.adapter.base.IMainAdapter;
import mzc.app.model.Customer;
import mzc.app.utils.reactive.State;
import mzc.app.view.components.member_list.LeftSideMemberListView;

public class MemberListPageViewModel extends SplitPageViewModel {
    public MemberListPageViewModel() {
        super("Daftar Member");
    }

    public static class MemberListContext {
        @Getter
        private final State<Customer> customer = new State<>(null);
        private final IMainAdapter adapter;
        public MemberListContext(IMainAdapter adapter) { this.adapter = adapter; }
        public void setCustomer(Customer customer) {
            this.adapter.getCustomer().persist(customer);
            this.customer.setValue(customer);
        }
    }
    @Override
    public void init() {
        super.init();
        this.setLeft(new LeftSideMemberListView());
    }

    @Override
    public void onTabFocus() { }
    @Override
    public void onTabClose() { }
}
