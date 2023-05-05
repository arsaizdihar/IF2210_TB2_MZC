package mzc.app.view_model.page;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import mzc.app.view_model.base.PageViewModel;
import org.jetbrains.annotations.NotNull;

@Getter @Setter
public class ReportPageViewModel extends PageViewModel {
    @Getter
    private final @NotNull VBox root = new VBox();
    @Getter
    private final @NotNull HBox headerBox = new HBox();
    @Getter
    private final @NotNull Pane spaser = new Pane();
    @Getter
    private final @NotNull Label titleLabel = new Label("Laporan");
    @Getter
    private final @NotNull Button printButton = new Button("Print To PDF");
    @Getter
    final @NotNull ListView<Node> listView = new ListView<>();
    public ReportPageViewModel() {
        super("Laporan Penjualan");
    }
    public void init() {
        super.init();
    }
    @Override
    public void onTabFocus() {

    }

    @Override
    public void onTabClose() {

    }
}
