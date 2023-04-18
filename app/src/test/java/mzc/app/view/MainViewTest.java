package mzc.app.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainViewTest extends JFXTest {
    @Test
    public void test() {
        MainView mainView = new MainView();
        Assertions.assertEquals(2, mainView.getRoot().getChildren().size());
        Assertions.assertEquals("", mainView.getViewModel().getWelcomeText().getText());
        mainView.getButton().fire();
        Assertions.assertEquals("Hello World!", mainView.getViewModel().getWelcomeText().getText());
    }
}