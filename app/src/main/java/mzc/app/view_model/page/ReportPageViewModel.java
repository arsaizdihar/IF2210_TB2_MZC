package mzc.app.view_model.page;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
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
    private final @NotNull HBox headerBox = new HBox();
    @Getter
    private final @NotNull Pane spaser = new Pane();
    @Getter
    private final @NotNull Label titleLabel = new Label("Laporan");
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

    public void createPrintButton() {

        printButton.setOnAction(event -> {
            PrintReport printReport = new PrintReport();
            try {
                var fixedBill = getAdapter().getFixedBill().getAll();
                for (var bill : fixedBill) {
                    printReport.setNewPage(bill, getAdapter().getProductHistory().getByBillId(bill.getId()));
                }
                printReport.toPrint("Dummy");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
