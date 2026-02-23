package seedu.address.ui.main.layout;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;
import seedu.address.ui.UiPart;
import seedu.address.ui.main.component.tab.dashboard.DashboardTab;
import seedu.address.ui.main.component.tab.directory.DirectoryTab;
import seedu.address.ui.main.component.tab.settings.SettingsTab;

/**
 * UI for the TabSection that is displayed on the left hand side of the main
 * window.
 */
public class TabSection extends UiPart<Region> {

    private static final String FXML = "main/layout/TabSection.fxml";

    private Logic logic;

    @FXML
    private StackPane directoryTabPlaceholder;

    @FXML
    private StackPane dashboardTabPlaceholder;

    @FXML
    private StackPane settingsTabPlaceholder;

    public TabSection(Logic logic) {
        super(FXML);
        this.logic = logic;
        fillInnerParts();
    }

    private void fillInnerParts() {
        DirectoryTab directoryTab = new DirectoryTab(logic.getSelectedPerson());
        directoryTabPlaceholder.getChildren().add(directoryTab.getRoot());

        DashboardTab dashboardTab = new DashboardTab();
        dashboardTabPlaceholder.getChildren().add(dashboardTab.getRoot());

        SettingsTab settingsTab = new SettingsTab();
        settingsTabPlaceholder.getChildren().add(settingsTab.getRoot());
    }
}
