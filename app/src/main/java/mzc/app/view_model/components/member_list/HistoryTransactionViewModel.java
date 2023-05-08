package mzc.app.view_model.components.member_list;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.Setter;
import mzc.app.model.Customer;
import mzc.app.modules.report.PrintTransactionHistory;
import mzc.app.view.components.member_list.HistoryTransactionEntryView;
import mzc.app.view_model.components.split_page.RightSideViewModel;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

@Getter
@Setter
public class HistoryTransactionViewModel extends RightSideViewModel {
    //    private final @NotNull VBox titleBox = new VBox();
//    private final @NotNull BorderPane infoBox = new BorderPane();
    @Getter
    @Setter
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

        if (fixedBills.isEmpty()) {
            Label emptyLabel = new Label("Tidak ada riwayat transaksi");
            entryBox.getChildren().add(emptyLabel);
            scPane.setContent(entryBox);
        } else {
            for (var fixedBill : fixedBills) {
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

    private void sendDialogEnd(String path) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Print Success");
        alert.setHeaderText(null);
        alert.setContentText("Print berhasil, file tersimpan di " + path);
        alert.showAndWait();
    }

    public void createPrintButton() {
        printButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF File", "*.pdf"));
            fileChooser.setInitialFileName("history_transaction-" + customer.getName() + ".pdf");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            File file = fileChooser.showSaveDialog(root.getScene().getWindow());
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
                        printTransactionHistory.toPrint(file.getAbsolutePath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> sendDialogEnd(file.getAbsolutePath()));
            });
            updateTimeThread.setDaemon(true);
            updateTimeThread.start();
        });
    }
}