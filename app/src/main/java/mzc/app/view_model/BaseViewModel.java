package mzc.app.view_model;

import lombok.Setter;
import mzc.app.adapter.AdapterManager;
import mzc.app.adapter.base.IMainAdapter;

public abstract class BaseViewModel {
    @Setter private AdapterManager adapterManager;

    public IMainAdapter getAdapter() {
        return adapterManager.getAdapter();
    }
}
