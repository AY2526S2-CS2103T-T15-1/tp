package seedu.address.ui.main.component.tab.directory;

import java.util.Optional;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.ui.UiPart;

/**
 * An editable panel that displays the detailed profile of a student.
 */
public class StudentProfilePanel extends UiPart<Region> {

    private static final String FXML = "main/component/tab/directory/StudentProfilePanel.fxml";
    /**
     * The person whose detail would be shown in the student profile panel
     **/
    private final ObjectProperty<Optional<Person>> selectedPerson;
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

    public StudentProfilePanel(ObjectProperty<Optional<Person>> selectedPerson) {
        super(FXML);
        this.selectedPerson = selectedPerson;

        // Initial render
        updateStudentProfilePanel(selectedPerson.get());

        // Listen for future changes
        this.selectedPerson.addListener((observable, oldValue, newValue) -> {
            updateStudentProfilePanel(newValue);
        });
    }

    private void updateStudentProfilePanel(Optional<Person> personOpt) {
        if (personOpt.isPresent()) {
            Person person = personOpt.get();
            nameField.setText(person.getName().fullName());
            genderField.setText("NOT IMPLEMENTED YET");
            blockField.setText("NOT IMPLEMENTED YET");
            floorField.setText("NOTE IMPLEMENTED YET");
            addressField.setText(person.getAddress().value());
            tagsField.setText(person.getTags().toString());
        } else {
            nameField.clear();
            genderField.clear();
            blockField.clear();
            floorField.clear();
            addressField.clear();
            tagsField.clear();
        }
    }
}
