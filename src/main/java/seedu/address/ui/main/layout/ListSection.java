package seedu.address.ui.main.layout;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;
import seedu.address.ui.UiPart;
import seedu.address.ui.main.component.list.FilterPanel;
import seedu.address.ui.main.component.list.PersonListPanel;

/**
 * UI for the ListSection that is displayed on the right hand side of the main
 * window.
 */
public class ListSection extends UiPart<Region> {

    private static final String FXML = "main/layout/ListSection.fxml";

    private final Logic logic;

    @FXML
    private StackPane filterPanelPlaceholder;

    @FXML
    private StackPane personListPanelPlaceholder;

    public ListSection(Logic logic) {
        super(FXML);
        this.logic = logic;
        fillInnerParts();
    }

    private void fillInnerParts() {
        FilterPanel filterPanel = new FilterPanel();
        filterPanelPlaceholder.getChildren().add(filterPanel.getRoot());

        PersonListPanel personListPanel = new PersonListPanel(logic.getFilteredPersonList(),
                logic.getSelectedPerson());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }
}
