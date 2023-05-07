package mzc.app.view_model.page;

import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import lombok.Getter;
import lombok.Setter;
import mzc.app.view_model.base.BaseViewModel;
import mzc.app.view_model.base.PageViewModel;
import org.jetbrains.annotations.NotNull;

public class PrintOptionViewModel extends PageViewModel {
    @Getter @Setter
    private Node nodeToPrint;
    @Getter
    private VBox root = new VBox();
    @Getter
    private final @NotNull Scene scene = new Scene(root);
    @Getter
    private final @NotNull Window window = root.getScene().getWindow();
    @Getter
    private final @NotNull TextArea textArea = new TextArea();
    @Getter
    private final @NotNull Button printButton = new Button("Print To PDF");

    public PrintOptionViewModel() {
        super("Print Option");
    }

    @Override
    public void init() {
        super.init();
        PrinterJob job = PrinterJob.createPrinterJob();
        if(job != null){
            job.showPrintDialog(window); // Window must be your main Stage
            job.printPage(nodeToPrint);
            job.endJob();
        }
    }

    @Override
    public void onTabFocus() {

    }
    @Override
    public void onTabClose() {

    }
}

