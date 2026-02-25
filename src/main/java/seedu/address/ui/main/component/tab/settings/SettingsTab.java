package seedu.address.ui.main.component.tab.settings;

import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * A ui for the settings tab that is displayed on the main window.
 */
public class SettingsTab extends UiPart<Region> {

    private static final String FXML = "main/component/tab/settings/SettingsTab.fxml";

    public SettingsTab() {
        super(FXML);
    }
}
