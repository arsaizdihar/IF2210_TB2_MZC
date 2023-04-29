package mzc.app.view_model.base;

import lombok.Getter;
import lombok.Setter;
import mzc.app.adapter.AdapterManager;
import mzc.app.adapter.base.IMainAdapter;
import mzc.app.utils.reactive.Context;
import mzc.app.view.base.BaseView;

import java.util.ArrayList;

public abstract class BaseViewModel {
    @Setter private AdapterManager adapterManager;
    @Getter final private ArrayList<Context<?>> contexts = new ArrayList<>();
    @Getter
    private BaseViewModel parentView;


    public IMainAdapter getAdapter() {
        return adapterManager.getAdapter();
    }

    public void addContext(Context<?> context) {
        for (int i = 0; i < contexts.size(); i++) {
            if (contexts.get(i).getClazz() == context.getClazz()) {
                contexts.set(i, context);
                return;
            }
        }
        contexts.add(context);
    }

    @SuppressWarnings("unchecked")
    public <T> Context<T> getContext(Class<T> clazz) {
        for (Context<?> context : contexts) {
            if (context.getClazz() == clazz) {
                return (Context<T>) context;
            }
        }
        return null;
    }

    public <U> Context<U> useContext(Class<U> clazz) {
        BaseViewModel parentView = this.getParentView();
        while (parentView != null) {
            Context<U> context = parentView.getContext(clazz);
            if (context != null) {
                return context;
            }
            parentView = parentView.getParentView();
        }
        return null;
    }

    protected void setParentView(BaseViewModel parentView) {
        this.parentView = parentView;
        this.init();
    }

    public <T extends BaseView<? extends BaseViewModel>>  T createView(Class<T> viewClass) {
        try {
            T view = viewClass.newInstance();
            view.getViewModel().setParentView(this);
            return view;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void init() {

    }
}
