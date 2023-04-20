package mzc.app.utils;

import mzc.app.adapter.AdapterManager;
import mzc.app.view_model.base.BaseViewModel;

public class ViewModelFactory {
    private static final AdapterManager adapterManager = new AdapterManager();

    public static void injectViewModel(BaseViewModel viewModel) {
        viewModel.setAdapterManager(adapterManager);
    }
}
