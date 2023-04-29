package mzc.app.view_model;

import lombok.Getter;
import mzc.app.utils.reactive.Context;
import mzc.app.view_model.base.BaseViewModel;

@Getter
public class MainViewModel extends BaseViewModel {
    Context<String> textContext = new Context<>("");

    public MainViewModel() {
        super();
        addContext(textContext);
    }
}