package mzc.app.view_model.components.member_list;

import mzc.app.utils.reactive.Context;
import mzc.app.view.components.member_list.AddMemberView;
import mzc.app.view.components.member_list.EditMemberView;
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
            setRightSideAddMember();
            getChildren().forceUpdate();
        });
    }

    public void reload() {
        var customers = getAdapter().getCustomer().getAll();
        getChildren().getValue().clear();

        var memberViews = customers.stream().map(customer -> {
            var memberview = new MemberView(customer);
            memberview.getViewModel().getTransactionButton().setOnAction((e) -> {
                HistoryTransactionView historyTransactionView = createView(new HistoryTransactionView(customer));
                Context<SplitPageViewModel.SplitPageContext> context = useContext(SplitPageViewModel.SplitPageContext.class);
                context.getValue().setRight(historyTransactionView);
                getChildren().forceUpdate();
            });
            memberview.getViewModel().getEditButton().setOnAction((e) -> {
                EditMemberView editMemberView = new EditMemberView(customer);
                Context<SplitPageViewModel.SplitPageContext> context = useContext(SplitPageViewModel.SplitPageContext.class);
                context.getValue().setRight(editMemberView);
                getChildren().forceUpdate();
            });

            createView(memberview);
            return memberview;
        });

        getChildren().getValue().addAll(memberViews.toList());
        getChildren().forceUpdate();
    }

    private void setRightSideAddMember() {
        // TODO Ganti dengan view yang sesuai yaitu Tambah/Ubah Member
        AddMemberView right = new AddMemberView();
        Context<SplitPageViewModel.SplitPageContext> context = useContext(SplitPageViewModel.SplitPageContext.class);
        context.getValue().setRight(right);
    }
}
