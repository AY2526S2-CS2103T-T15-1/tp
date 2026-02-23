package seedu.address.ui.main.component.tab.directory;

import java.util.Optional;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.ui.UiPart;

/**
 * A panel containing a table of Demerit records.
 */
public class DemeritRecordsPanel extends UiPart<Region> {

    /** The person whose demerit records would be shown **/
    private ObservableValue<Optional<Person>> selectedPerson;

    private static final String FXML = "main/component/tab/directory/DemeritRecordsPanel.fxml";

    @FXML
    private TableView<DemeritRecordDummy> demeritTableView;
    @FXML
    private TableColumn<DemeritRecordDummy, Integer> indexColumn;
    @FXML
    private TableColumn<DemeritRecordDummy, String> dateColumn;
    @FXML
    private TableColumn<DemeritRecordDummy, String> descriptionColumn;
    @FXML
    private TableColumn<DemeritRecordDummy, Integer> severityColumn;

    public DemeritRecordsPanel(ObservableValue<Optional<Person>> selectedPerson) {
        super(FXML);

        // Initialize columns
        indexColumn.setCellFactory(col -> new javafx.scene.control.TableCell<DemeritRecordDummy, Integer>() {
            @Override
            public void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        severityColumn.setCellValueFactory(new PropertyValueFactory<>("severity"));

        // Placeholder data for UI testing
        ObservableList<DemeritRecordDummy> placeholderData = FXCollections.observableArrayList(
                new DemeritRecordDummy("2023-10-15", "Noise Disturbance", 1));
        demeritTableView.setItems(placeholderData);
    }

    // Dummy class for UI placeholder binding
    public static class DemeritRecordDummy {
        private final String date;
        private final String description;
        private final int severity;

        public DemeritRecordDummy(String date, String description, int severity) {
            this.date = date;
            this.description = description;
            this.severity = severity;
        }

        public String getDate() {
            return date;
        }

        public String getDescription() {
            return description;
        }

        public int getSeverity() {
            return severity;
        }
    }
}
