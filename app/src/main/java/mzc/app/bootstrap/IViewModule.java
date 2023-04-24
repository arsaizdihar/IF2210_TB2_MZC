package mzc.app.bootstrap;

import mzc.app.view.base.BaseView;
import mzc.app.view_model.base.BaseViewModel;

interface IViewModule {
    ViewType getType();

    BaseView<? extends BaseViewModel> getView();
}
