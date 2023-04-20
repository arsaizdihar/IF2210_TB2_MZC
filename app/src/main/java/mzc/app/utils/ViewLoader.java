package mzc.app.utils;

import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.base.BaseViewModel;

public class ViewLoader {
    public static <T extends BaseViewModel> void load(BaseView<T> view) {
        try {
            @SuppressWarnings("unchecked")
            Class<? extends BaseView<T>> reflectedClass = (Class<? extends BaseView<T>>) view.getClass();
            if (reflectedClass.isAnnotationPresent(ModelInject.class)) {
                Class<? extends BaseViewModel> viewModelClass = reflectedClass.getAnnotation(ModelInject.class).value();
                @SuppressWarnings("unchecked")
                T viewModel = (T) viewModelClass.newInstance();
                view.setViewModel(viewModel);
                ViewModelFactory.injectViewModel(viewModel);
            } else {
                throw new RuntimeException("View class must be annotated with @ModelInject");
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
