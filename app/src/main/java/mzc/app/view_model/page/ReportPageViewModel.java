package mzc.app.view_model.page;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import mzc.app.model.FixedBill;
import mzc.app.modules.report.PrintReport;
import mzc.app.view.components.report.ReportEntryView;
import mzc.app.view.page.PrintOptionView;
import mzc.app.view_model.base.PageViewModel;
import mzc.app.view_model.components.report.ReportEntryViewModel;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Getter @Setter
public class ReportPageViewModel extends PageViewModel {
    @Getter
    private final @NotNull VBox root = new VBox();
    @Getter
    private final @NotNull BorderPane padder = new BorderPane();
    @Getter
    private final @NotNull Label titleLabel = new Label("Laporan Penjualan");
    @Getter
    private final @NotNull Button printButton = new Button("Print To PDF");
    @Getter
    private final @NotNull ScrollPane scrollPane = new ScrollPane();
    @Getter
    private final @NotNull VBox container = new VBox();
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

    public void createEntry(FixedBill fixedBill) {
        var entry = new ReportEntryView(fixedBill);
        createView(entry);
        container.getChildren().add(entry.getView());
    }

    public void iterateFixedBill() {
        var fixedBills = getAdapter().getFixedBill().getAll();
        for (var fixedBill : fixedBills) {
            createEntry(fixedBill);
        }
    }
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
        alert.setContentText("Print berhasil, file tersimpan di app/src/main/resources/mzc/app/assets/Report.pdf");
        alert.showAndWait();
    }

    public void createPrintButton() {
        printButton.setOnAction(event -> {
            sendDialogStart();
            Thread updateTimeThread = new Thread(() -> {
                    try {
                        Thread.sleep(10000);
                        PrintReport printReport = new PrintReport();
                        try {
                            var fixedBill = getAdapter().getFixedBill().getAll();
                            for (var bill : fixedBill) {
                                printReport.setNewPage(bill, getAdapter().getProductHistory().getByBillId(bill.getId()));
                            }
                            printReport.toPrint("app/src/main/resources/mzc/app/assets/Report.pdf");

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
