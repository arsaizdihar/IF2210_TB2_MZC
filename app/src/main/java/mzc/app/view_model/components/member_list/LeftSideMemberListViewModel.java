package mzc.app.view_model.components.member_list;

import mzc.app.utils.reactive.Context;
import mzc.app.view.components.member_list.AddMemberView;
import mzc.app.view.components.member_list.HistoryTransactionView;
import mzc.app.view.components.member_list.MemberView;
import mzc.app.view_model.components.split_page.LeftSideViewModel;
import mzc.app.view_model.page.SplitPageViewModel;

public class LeftSideMemberListViewModel extends LeftSideViewModel {

    @Override
    public void init() {
        super.init();
        this.reload();
        setOnButtonClicked((e) -> {
            // getChildren().getValue().add(createView(new HistoryTransactionPageView()));
            setRightSideAddMember();
            getChildren().forceUpdate();
        });
    }

    public void reload() {
        getListView().getItems().clear();

        var customers = getAdapter().getCustomer().getAll();

        var memberViews = customers.stream().map(customer -> {
            var memberview = new MemberView(customer);
            memberview.getViewModel().getTransactionButton().setOnAction((e) -> {
                HistoryTransactionView historyTransactionView = new HistoryTransactionView(customer);
                Context<SplitPageViewModel.SplitPageContext> context = useContext(SplitPageViewModel.SplitPageContext.class);
                context.getValue().setRight(historyTransactionView);
                getChildren().forceUpdate();

            });
            createView(memberview);
            return memberview.getView();
        });

        getListView().getItems().addAll(memberViews.toList());
    }

    private void setRightSideAddMember() {
        // TODO Ganti dengan view yang sesuai yaitu Tambah/Ubah Member
        AddMemberView right = new AddMemberView();
        Context<SplitPageViewModel.SplitPageContext> context = useContext(SplitPageViewModel.SplitPageContext.class);
        context.getValue().setRight(right);
    }

    private void setRightSideEditMember() { }
}
