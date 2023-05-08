package mzc.app.view.components.cashier;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.cashier.ItemsViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(ItemsViewModel.class)
public class ItemsView extends BaseView<ItemsViewModel> {
    @Override
    public @NotNull Node getView() {
        var container = new VBox();

        var title = new Label("Daftar Barang");
        title.getStyleClass().add("h4");
        title.setStyle("-fx-font-weight: bold;");

        var items = getViewModel().getItemList();
        items.setPadding(new Insets(0, 30, 20, 10));
        container.getChildren().add(title);
        container.getChildren().add(getViewModel().getScrollable());
        container.setMaxHeight(300);

        return container;
    }
}
