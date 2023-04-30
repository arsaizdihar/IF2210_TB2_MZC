package mzc.app.plugins.base;

import mzc.app.view.base.BaseView;
import mzc.app.view_model.base.BaseViewModel;

interface IViewModule {
    ViewType getType();

    BaseView<? extends BaseViewModel> createView();
}
