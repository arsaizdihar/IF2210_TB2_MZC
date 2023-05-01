package mzc.app.view_model.base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import mzc.app.adapter.AdapterManager;
import mzc.app.adapter.base.IMainAdapter;
import mzc.app.utils.reactive.Context;
import mzc.app.view.MainView;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.MainViewModel;

import java.lang.reflect.InvocationTargetException;
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
        BaseViewModel parentView = this;
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
            T view = viewClass.getDeclaredConstructor().newInstance();
            return createView(view);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends BaseView<? extends BaseViewModel>>  T createView(T view) {
        view.getViewModel().setParentView(this);
        return view;
    }

    public void init() {

    }
}
