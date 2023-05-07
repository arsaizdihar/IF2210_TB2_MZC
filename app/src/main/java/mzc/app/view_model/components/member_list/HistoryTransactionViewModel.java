package mzc.app.view_model.components.member_list;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import lombok.Getter;
import lombok.Setter;
import mzc.app.model.Customer;
import mzc.app.model.FixedBill;
import mzc.app.modules.report.PrintReport;
import mzc.app.modules.report.PrintTransactionHistory;
import mzc.app.view.components.member_list.HistoryTransactionEntryView;
import mzc.app.view_model.components.split_page.RightSideViewModel;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Set;

@Getter @Setter
public class HistoryTransactionViewModel extends RightSideViewModel {
//    private final @NotNull VBox titleBox = new VBox();
//    private final @NotNull BorderPane infoBox = new BorderPane();
    @Getter @Setter
    private Customer customer;
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

    public void showFixedBills() {
        System.out.println(getCustomer().getName());
        var fixedBills = getAdapter().getFixedBill().getByCustomerId(getCustomer().getId());
        System.out.println(fixedBills);

        if (fixedBills.isEmpty()){
            Label emptyLabel = new Label("Tidak ada riwayat transaksi");
            root.getChildren().add(emptyLabel);
        } else {
            for (var fixedBill: fixedBills) {
                var productHistories = getAdapter().getFixedBill().getProducts(fixedBill);
                var historyTransactionEntryView = new HistoryTransactionEntryView(productHistories);
                createView(historyTransactionEntryView);
//                System.out.println(fixedBill.getProducts());
//                historyTransactionEntryView.getViewModel().setFixedBill(fixedBill);
//                System.out.println(historyTransactionEntryView.getViewModel().getFixedBill().getProducts());
                root.getChildren().add(historyTransactionEntryView.getView());
            }
        }
    }
    public void createPrintButton() {

        printButton.setOnAction(event -> {
            PrintTransactionHistory printTransactionHistory = new PrintTransactionHistory();
            try {
                var fixedBills = getAdapter().getFixedBill().getByCustomerId(getCustomer().getId());
                for (var bill : fixedBills) {
                    printTransactionHistory.printPage(bill, getAdapter().getProductHistory().getByBillId(bill.getId()), getCustomer().getName());
                }
                printTransactionHistory.toPrint("Dummy");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


}