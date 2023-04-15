package mzc.app.utils;

import mzc.app.adapter.AdapterManager;
import mzc.app.view_model.BaseViewModel;

import java.lang.reflect.InvocationTargetException;

public class ViewModelFactory {
    private static final AdapterManager adapterManager = new AdapterManager();
    public static <T> T injectViewModel(Class<T> clazz) {
        try {
            T viewModelInstance = clazz.getDeclaredConstructor().newInstance();
            if (viewModelInstance instanceof BaseViewModel) {
                ((BaseViewModel) viewModelInstance).setAdapterManager(adapterManager);
            } else {
                throw new RuntimeException("View model must extends BaseViewModel");
            }
            return viewModelInstance;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
