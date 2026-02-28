package seedu.address.ui.main.component.list;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "main/component/list/PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList} and
     * {@code Logic}.
     */
    public PersonListPanel(ObservableList<Person> personList,
                           ObjectProperty<Optional<Person>> selectedPerson) {
        super(FXML);

        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        syncSelectedPerson(selectedPerson);
    }

    /**
     * Synchronizes the selected person in the ListView with the Logic and Model's
     * selected person.
     */
    private void syncSelectedPerson(ObjectProperty<Optional<Person>> selectedPerson) {
        // Syncs the change from UI to Logic and Model
        personListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, lastSelected, newSelected) ->
                        onUiSelectionChanged(newSelected, selectedPerson));

        // Syncs the change from Logic and Model to UI
        selectedPerson.addListener((observable, lastSelected, newSelected) ->
                onModelSelectionChanged(lastSelected, newSelected));
    }

    private void onUiSelectionChanged(Person newSelected, ObjectProperty<Optional<Person>> selectedPerson) {
        if (newSelected == null) {
            logger.fine("Selection in person list is now empty");
            selectedPerson.set(Optional.empty());
            return;
        }

        Optional<Person> current = selectedPerson.getValue();
        if (current.isPresent() && current.get().equals(newSelected)) {
            return;
        }

        logger.fine("Selection in person list changed to : '" + newSelected.getName().fullName() + "'");
        selectedPerson.set(Optional.of(newSelected));
    }

    private void onModelSelectionChanged(Optional<Person> oldSelected,
                                         Optional<Person> newSelected) {
        // Clear selection in List Panel if user clears selected person
        if (newSelected.isEmpty() && oldSelected.isPresent()) {
            personListView.getSelectionModel().clearSelection();
            return;
        }

        Person person = newSelected.get();

        // If selected person has not changed, do nothing
        if (person.equals(personListView.getSelectionModel().getSelectedItem())) {
            return;
        }

        // Select and scroll to the newly selected person
        personListView.getSelectionModel().select(person);
        scrollToPersonCentered(person);
    }

    /**
     * Scrolls the ListView to the person and centers it in the view if possible.
     */
    private void scrollToPersonCentered(Person person) {
        int index = personListView.getItems().indexOf(person);
        assert (index >= 0) : "Selected person must exist in the list";
        Platform.runLater(() -> personListView.scrollTo(index));
    }
}
