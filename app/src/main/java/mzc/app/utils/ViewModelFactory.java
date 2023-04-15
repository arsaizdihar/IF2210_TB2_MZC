package mzc.app.utils;

import mzc.app.view_model.BaseViewModel;

import java.lang.reflect.InvocationTargetException;

public class ViewModelFactory {
    public static <T> T injectViewModel(Class<T> clazz) {
        try {
            T viewModelInstance = clazz.getDeclaredConstructor().newInstance();
            if (viewModelInstance instanceof BaseViewModel) {
//                inject view model with data store adapter
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
