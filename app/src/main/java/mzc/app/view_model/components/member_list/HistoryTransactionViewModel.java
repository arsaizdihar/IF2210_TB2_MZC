package mzc.app.view_model.components.member_list;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
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
    private final @NotNull VBox entryBox = new VBox();
    @Getter
    private final @NotNull Label title = new Label("Riwayat Transaksi");
    @Getter
    private final @NotNull Pane spaser = new Pane();
    @Getter
    private Label infoUser = new Label();
    @Getter
    private Button printButton = new Button("Print To PDF");
    @Getter
    final @NotNull ListView<Node> listView = new ListView<>();
    @Getter
    private final @NotNull VBox tableBox = new VBox();
    @Getter
    private final @NotNull ScrollPane scPane = new ScrollPane();

    @Override
    public void init() {
        super.init();
        infoUser.setText("Riwayat Transaksi " + customer.getName());

    }

    public void showFixedBills() {
        var fixedBills = getAdapter().getFixedBill().getByCustomerId(getCustomer().getId());

        if (fixedBills.isEmpty()){
            Label emptyLabel = new Label("Tidak ada riwayat transaksi");
            entryBox.getChildren().add(emptyLabel);
            scPane.setContent(entryBox);
        } else {
            for (var fixedBill: fixedBills) {
                var productHistories = getAdapter().getFixedBill().getProducts(fixedBill);
                var historyTransactionEntryView = new HistoryTransactionEntryView(productHistories, fixedBill);
                createView(historyTransactionEntryView);
                entryBox.getChildren().add(historyTransactionEntryView.getView());
                scPane.setContent(entryBox);
            }
        }
    }
//    public void createPrintButton() {
//
//        printButton.setOnAction(event -> {
//            PrintTransactionHistory printTransactionHistory = new PrintTransactionHistory();
//            try {
//                var fixedBills = getAdapter().getFixedBill().getByCustomerId(getCustomer().getId());
//                for (var bill : fixedBills) {
//                    printTransactionHistory.printPage(bill, getAdapter().getProductHistory().getByBillId(bill.getId()), getCustomer().getName());
//                }
//                printTransactionHistory.toPrint("app/src/main/resources/mzc/app/assets/HistoryTransaction.pdf");
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
    private void sendDialogStart() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Print Success");
        alert.setHeaderText(null);
        alert.setContentText("Proses print sedang berlangsung, mohon tunggu");
        alert.show();
    }

    private void sendDialogEnd() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Print Success");
        alert.setHeaderText(null);
        alert.setContentText("Print berhasil, file tersimpan di app/src/main/resources/mzc/app/assets/HistoryTransaction.pdf");
        alert.showAndWait();
    }

    public void createPrintButton() {
        printButton.setOnAction(event -> {
            sendDialogStart();
            Thread updateTimeThread = new Thread(() -> {
                try {
                    Thread.sleep(10000);
                    PrintTransactionHistory printTransactionHistory = new PrintTransactionHistory();
                    try {
                        var fixedBills = getAdapter().getFixedBill().getByCustomerId(getCustomer().getId());
                        for (var bill : fixedBills) {
                            printTransactionHistory.printPage(bill, getAdapter().getProductHistory().getByBillId(bill.getId()), getCustomer().getName());
                        }
                        printTransactionHistory.toPrint("app/src/main/resources/mzc/app/assets/HistoryTransaction.pdf");

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(this::sendDialogEnd);
            });
            updateTimeThread.setDaemon(true);
            updateTimeThread.start();
        });
    }
}