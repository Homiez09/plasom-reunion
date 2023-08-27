package cs211.project.services;

import javafx.scene.control.TabPane;
import javafx.scene.input.KeyEvent;

public class BlockArrowKeyFromTabPane {
    public BlockArrowKeyFromTabPane(TabPane mainTab) {
        mainTab.addEventFilter(KeyEvent.ANY, event -> {
            if (event.getCode().isArrowKey()) {
                event.consume();
            }
        });
    }
}
