package mzc.app.view_model.components.member_list;

import lombok.Getter;
import mzc.app.utils.reactive.Context;
import mzc.app.view.components.member_list.AddMemberView;
import mzc.app.view.components.member_list.EditMemberView;
import mzc.app.view.components.member_list.HistoryTransactionView;
import mzc.app.view.components.member_list.MemberView;
import mzc.app.view_model.components.split_page.LeftSideViewModel;
import mzc.app.view_model.page.SplitPageViewModel;

public class LeftSideMemberListViewModel extends LeftSideViewModel {
    public static class ReloadContext {
        private final Runnable reload;

        public ReloadContext(Runnable reload) {
            this.reload = reload;
        }

        public void reload() {
            reload.run();
        }
    }
    @Override
    public void init() {
        super.init();
        getButton().setVisible(false);
        Context<ReloadContext> context = new Context<>(new ReloadContext(() -> this.reload()));
        addContext(context);

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
        }).toList();

        getChildren().getValue().addAll(memberViews);
        getChildren().forceUpdate();
    }

    private void setRightSideAddMember() {
        AddMemberView right = new AddMemberView();
        Context<SplitPageViewModel.SplitPageContext> context = useContext(SplitPageViewModel.SplitPageContext.class);
        context.getValue().setRight(right);
    }
}
