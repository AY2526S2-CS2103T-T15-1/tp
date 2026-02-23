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
 * A panel containing a table of CCA records.
 */
public class CcaRecordsPanel extends UiPart<Region> {

    /** The person whose detail would be shown in the CCA Records panel **/
    private ObservableValue<Optional<Person>> selectedPerson;

    private static final String FXML = "main/component/tab/directory/CcaRecordsPanel.fxml";

    @FXML
    private TableView<CcaRecordDummy> ccaTableView;
    @FXML
    private TableColumn<CcaRecordDummy, Integer> indexColumn;
    @FXML
    private TableColumn<CcaRecordDummy, Integer> pointsColumn;
    @FXML
    private TableColumn<CcaRecordDummy, String> timeColumn;
    @FXML
    private TableColumn<CcaRecordDummy, String> descriptionColumn;

    public CcaRecordsPanel(ObservableValue<Optional<Person>> selectedPerson) {
        super(FXML);

        // Initialize columns
        indexColumn.setCellFactory(col -> new javafx.scene.control.TableCell<CcaRecordDummy, Integer>() {
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
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Placeholder data for UI testing
        ObservableList<CcaRecordDummy> placeholderData = FXCollections.observableArrayList(
                new CcaRecordDummy(10, "Y1S1", "Hall Exco"),
                new CcaRecordDummy(5, "Y1S2", "IFG Soccer"));
        ccaTableView.setItems(placeholderData);
    }

    // Dummy class for UI placeholder binding
    public static class CcaRecordDummy {
        private final int points;
        private final String time;
        private final String description;

        public CcaRecordDummy(int points, String time, String description) {
            this.points = points;
            this.time = time;
            this.description = description;
        }

        public int getPoints() {
            return points;
        }

        public String getTime() {
            return time;
        }

        public String getDescription() {
            return description;
        }
    }
}
