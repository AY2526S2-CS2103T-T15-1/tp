package seedu.address.ui.main.component.tab.directory;

import java.util.Optional;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.person.Person;
import seedu.address.ui.UiPart;

/**
 * A ui for the main tab that is displayed on the main window.
 */
public class DirectoryTab extends UiPart<Region> {

    private static final String FXML = "main/component/tab/directory/DirectoryTab.fxml";
    /**
     * The person whose details would be shown in the directory tab
     **/
    private final ObjectProperty<Optional<Person>> selectedPerson;
    @FXML
    private TabPane detailsTabPane;

    @FXML
    private StackPane profilePlaceholder;

    @FXML
    private StackPane ccaRecordsPlaceholder;

    @FXML
    private StackPane demeritRecordsPlaceholder;

    public DirectoryTab(ObjectProperty<Optional<Person>> selectedPerson) {
        super(FXML);
        this.selectedPerson = selectedPerson;
        fillInnerParts();
    }

    private void fillInnerParts() {
        StudentProfilePanel studentProfilePanel = new StudentProfilePanel(selectedPerson);
        profilePlaceholder.getChildren().add(studentProfilePanel.getRoot());

        CcaRecordsPanel ccaRecordsPanel = new CcaRecordsPanel(selectedPerson);
        ccaRecordsPlaceholder.getChildren().add(ccaRecordsPanel.getRoot());

        DemeritRecordsPanel demeritRecordsPanel = new DemeritRecordsPanel(selectedPerson);
        demeritRecordsPlaceholder.getChildren().add(demeritRecordsPanel.getRoot());
    }
}
