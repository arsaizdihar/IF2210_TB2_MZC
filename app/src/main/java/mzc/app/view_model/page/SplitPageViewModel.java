package mzc.app.view_model.page;
import javafx.scene.layout.*;
import lombok.Getter;
import mzc.app.utils.reactive.Context;
import mzc.app.view.components.split_view.LeftSideView;
import mzc.app.view.components.split_view.RightSideView;
import mzc.app.view_model.base.PageViewModel;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public abstract class SplitPageViewModel extends PageViewModel {
    @Getter
    private LeftSideView<?> left;

    @Getter
    private RightSideView<?> right;

    @Getter
    private final @NotNull GridPane splitView = new GridPane();

    public static class SplitPageContext {
        SplitPageContext(Consumer<RightSideView<?>> consumer) {
            this.consumer = consumer;
        }
        private final Consumer<RightSideView<?>> consumer;
        public void setRight(RightSideView<?> rightSideView) {
            consumer.accept(rightSideView);
        }
    }
    public SplitPageViewModel(String title) {
        super(title);
        Context<SplitPageContext> context = new Context<>(new SplitPageContext(this::setRight));
        splitView.setId("split-page");
        this.addContext(context);
        var col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        col1.setPercentWidth(60);
        var col2 = new ColumnConstraints();
        col2.setPercentWidth(40);
        col2.setHgrow(Priority.ALWAYS);
        var row = new RowConstraints();
        row.setPercentHeight(100);
        row.setVgrow(Priority.ALWAYS);
        splitView.getColumnConstraints().addAll(col1, col2);
        splitView.getRowConstraints().add(row);
    }

    public void setLeft(LeftSideView<?> left) {
        createView(left);
        this.left = left;
        splitView.add(left.getView(), 0, 0);
    }

    public void setRight(RightSideView<?> right) {
        assert left != null: "Must set left side first";
        if (this.right == right) return;
        if (this.right != null) {
            splitView.getChildren().remove(this.right.getView());
        }
        this.right = right;
        if (right == null) {
            splitView.add(null, 1, 0);
        } else {
            left.getViewModel().createView(right);
            splitView.add(right.getView(), 1, 0);
        }
    }
}
