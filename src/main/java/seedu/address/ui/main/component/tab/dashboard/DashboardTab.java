package seedu.address.ui.main.component.tab.dashboard;

import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * A ui for the dashboard tab that is displayed on the main window.
 */
public class DashboardTab extends UiPart<Region> {

    private static final String FXML = "main/component/tab/dashboard/DashboardTab.fxml";

    public DashboardTab() {
        super(FXML);
    }
}
