package seedu.address.ui;

import java.util.Set;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.FilterDetails;
import seedu.address.ui.filter.FilterPanelTag;

/**
 * Panel containing the list of filtering and sorting options.
 */
public class FilterPanel extends UiPart<Region> {
    private static final String FXML = "FilterPanel.fxml";

    private final FilterDetails filterDetails;

    @FXML
    private TextField nameFilterField;
    @FXML
    private FlowPane nameTags;
    @FXML
    private TextField phoneNumberFilterField;
    @FXML
    private TextField emailFilterField;
    @FXML
    private TextField studentIdFilterField;
    @FXML
    private TextField roomNumberFilterField;
    @FXML
    private TextField majorFilterField;
    @FXML
    private TextField emergencyContactFilterField;
    @FXML
    private ComboBox<String> floorFilterComboBox;
    @FXML
    private ComboBox<String> yearFilterComboBox;
    @FXML
    private ComboBox<String> genderFilterComboBox;
    @FXML
    private ComboBox<String> sortByComboBox;
    @FXML
    private FontIcon filterIcon;
    @FXML
    private FontIcon sortIcon;

    /**
     * Creates a {@code FilterPanel} with default filter and sort options.
     */
    public FilterPanel() {
        super(FXML);

        // Initialize filterDetails with default values for demonstration purposes
        this.filterDetails = new FilterDetails();
        filterDetails.getNameKeywords().addListener((SetChangeListener<String>) change -> updateNameTags());
        filterDetails.setNameKeywords(Set.of("Alice", "Bob", "Charlie", "Daniel", "Eve"));

        // Initialize dummy values for ComboBoxes for UI demonstration
        floorFilterComboBox.getItems().addAll("Any", "1", "2", "3", "4", "5");
        floorFilterComboBox.getSelectionModel().selectFirst();

        yearFilterComboBox.getItems().addAll("Any", "1", "2", "3", "4", "5+");
        yearFilterComboBox.getSelectionModel().selectFirst();

        genderFilterComboBox.getItems().addAll("Any", "Male", "Female");
        genderFilterComboBox.getSelectionModel().selectFirst();

        sortByComboBox.getItems().addAll("None", "Name", "Phone", "Email", "Student ID", "Room number", "Major",
                "Emergency Contact", "Floor", "Year", "Gender");
        sortByComboBox.getSelectionModel().selectFirst();

        sortByComboBox.getItems().addAll("Ascending", "Descending");
        sortByComboBox.getSelectionModel().selectFirst();
    }

    private void updateNameTags() {
        nameTags.getChildren().clear();
        for (String keyword : filterDetails.getNameKeywords()) {
            nameTags.getChildren().add(new FilterPanelTag(keyword).getRoot());
        }
    }

    /*
    * Handles the event when the user presses 'Enter' in the name filter field.
    * Splits the input into individual keywords and displays them as tags in the UI.
     */
    @FXML
    private void handleNameFieldEntered() {
        String nameFilterText = nameFilterField.getText();
        if (nameFilterText.trim().isEmpty()) {
            return;
        }
        Set<String> nameFilterKeywordsSet = StringUtil.splitSentenceIntoWords(nameFilterText);
        filterDetails.setNameKeywords(nameFilterKeywordsSet);
        nameFilterField.clear();
    }
}
