package seedu.address.ui.main.component.list;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.person.Person;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "main/component/list/PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    private final Logic logic;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList} and
     * {@code Logic}.
     */
    public PersonListPanel(ObservableList<Person> personList, Logic logic) {
        super(FXML);
        this.logic = logic;

        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        // Listen to selection changes and update the Logic property directly
        personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in person list changed to : '" + newValue + "'");
            if (newValue == null) {
                // If selection clears (or list is updated), we set empty in the logic layer
                // However, currently Logic doesn't expose `set`.
                // A better pattern for UI-driven selection is executing a command, OR
                // since we want to avoid commands for simple clicks, we use the property
                // interface.
                this.logic.getSelectedPerson().set(java.util.Optional.empty());
            } else {
                this.logic.getSelectedPerson().set(java.util.Optional.of(newValue));
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using
     * a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);
            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
