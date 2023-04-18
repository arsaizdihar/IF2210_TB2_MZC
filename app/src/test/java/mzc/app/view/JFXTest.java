package mzc.app.view;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;

public class JFXTest {
    @BeforeAll
    static void beforeAll() {
//        this is a hack to initialize JavaFX Toolkit
        JFXPanel jfxPanel = new JFXPanel();
    }
}
