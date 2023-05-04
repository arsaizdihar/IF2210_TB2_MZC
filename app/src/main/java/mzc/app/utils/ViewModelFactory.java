package mzc.app.utils;

import mzc.app.adapter.Datastore;
import mzc.app.view_model.base.BaseViewModel;

public class ViewModelFactory {
    public static void injectViewModel(BaseViewModel viewModel) {
        viewModel.setAdapterManager(Datastore.getManager());
    }
}
