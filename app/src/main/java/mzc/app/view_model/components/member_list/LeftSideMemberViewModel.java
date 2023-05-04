package mzc.app.view_model.components.member_list;

import mzc.app.utils.reactive.Context;
import mzc.app.view.components.member_list.HistoryTransactionPageView;
import mzc.app.view_model.components.split_page.LeftSideViewModel;
import mzc.app.view_model.page.SplitPageViewModel;

public class LeftSideMemberViewModel extends LeftSideViewModel {
    @Override
    public void init() {
        super.init();
        setOnButtonClicked((e) -> {
            // getChildren().getValue().add(createView(new HistoryTransactionPageView()));
            setRightSide();
            getChildren().forceUpdate();
        });
    }

    private void setRightSide() {
        // TODO Ganti dengan view yang sesuai yaitu Tambah/Ubah Member
        HistoryTransactionPageView right = new HistoryTransactionPageView();
        Context<SplitPageViewModel.SplitPageContext> context = useContext(SplitPageViewModel.SplitPageContext.class);
        context.getValue().setRight(right);
    }
}
