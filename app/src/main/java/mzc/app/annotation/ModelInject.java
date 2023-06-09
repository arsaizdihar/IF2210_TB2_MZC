package mzc.app.annotation;

import mzc.app.view_model.base.BaseViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModelInject {
    Class<? extends BaseViewModel> value();
}
