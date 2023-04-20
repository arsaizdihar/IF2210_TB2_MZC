package mzc.app.view_model.page;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.Getter;
import lombok.Setter;
import mzc.app.view.page.ParamPageView;
import mzc.app.view_model.base.PageViewModel;

@Getter @Setter
public class MainPageViewModel extends PageViewModel {
    private Button button = new Button("Change page");

    public MainPageViewModel() {
        super("MZC");
        button.setOnAction(event -> {
            ParamPageView paramPageView = new ParamPageView("test param");
            changePage(paramPageView);
        });
    }
}
