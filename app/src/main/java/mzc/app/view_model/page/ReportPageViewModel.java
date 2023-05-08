package mzc.app.view_model.page;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.Setter;
import mzc.app.model.FixedBill;
import mzc.app.modules.report.PrintReport;
import mzc.app.view.components.report.ReportEntryView;
import mzc.app.view_model.base.PageViewModel;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

@Getter
@Setter
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
            fileChooser.setInitialFileName("report.pdf");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            File file = fileChooser.showSaveDialog(root.getScene().getWindow());
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
                        printReport.toPrint(file.getAbsolutePath());

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    sendDialogEnd(file.getAbsolutePath());
                });
            });
            updateTimeThread.setDaemon(true);
            updateTimeThread.start();
        });
    }
}
