package mzc.app.view_model.components.member_list;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lombok.Getter;
import lombok.Setter;
import mzc.app.utils.reactive.Context;
import mzc.app.view_model.base.PageViewModel;
import mzc.app.view_model.components.split_page.RightSideViewModel;
import org.jetbrains.annotations.NotNull;

@Getter @Setter
public class HistoryTransactionPageViewModel extends RightSideViewModel {
//    private final @NotNull VBox titleBox = new VBox();
//    private final @NotNull BorderPane infoBox = new BorderPane();
    @Getter
    private VBox root = new VBox();
    @Getter
    private final @NotNull Label title = new Label("Riwayat Transaksi");
    @Getter
    final @NotNull HBox infoBox = new HBox();
    @Getter
    private final @NotNull Pane spaser = new Pane();
    @Getter
    private final @NotNull Label infoUser = new Label("Riwatat Transaksi User");
    @Getter
    private Button printButton = new Button("Print To PDF");
    @Getter
    final @NotNull ListView<Node> listView = new ListView<>();
    @Getter
    private final @NotNull VBox tableBox = new VBox();

    @Override
    public void init() {
        super.init();





    }

}