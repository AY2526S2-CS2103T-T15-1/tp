package seedu.address.ui.main.component.tab.directory;

import java.util.Optional;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.ui.UiPart;

/**
 * An editable panel that displays the detailed profile of a student.
 */
public class StudentProfilePanel extends UiPart<Region> {

    /** The person whose detail would be shown in the student profile panel **/
    private ObservableValue<Optional<Person>> selectedPerson;

    private static final String FXML = "main/component/tab/directory/StudentProfilePanel.fxml";

    @FXML
    private TextField nameField;
    @FXML
    private TextField genderField;
    @FXML
    private TextField blockField;
    @FXML
    private TextField floorField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField tagsField;

    public StudentProfilePanel(ObservableValue<Optional<Person>> selectedPerson) {
        super(FXML);
        // Will map Person properties to these fields later during Phase 4 integration
    }
}
