package seedu.address.ui.main.layout;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;
import seedu.address.model.person.Person;
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

    private final Logic logic;

    @FXML
    private TabPane tabPaneRoot;

    @FXML
    private Tab directoryTab;

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

        // Listen to changes in the selected person and switch to the directory tab when a new person is selected
        setupSelectedPersonListener();
    }

    private void setupSelectedPersonListener() {
        logic.getSelectedPerson()
                .addListener((observable, oldValue, newValue) -> handlePersonSelectionChange(newValue));
    }

    private void handlePersonSelectionChange(Optional<Person> selectedPerson) {
        if (selectedPerson.isPresent()) {
            tabPaneRoot.getSelectionModel().select(directoryTab);
        }
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
