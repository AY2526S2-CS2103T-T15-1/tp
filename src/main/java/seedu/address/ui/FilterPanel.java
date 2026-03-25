package seedu.address.ui;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FilterDetails;
import seedu.address.model.ReadOnlyFilterDetails;
import seedu.address.ui.executors.FilterExecutor;
import seedu.address.ui.filter.FilterPanelField;

/**
 * Panel containing the list of filtering and sorting options.
 */
public class FilterPanel extends UiPart<Region> {
    private static final String FXML = "FilterPanel.fxml";
    private final ReadOnlyFilterDetails filterDetails;
    private final FilterExecutor filterExecutor;

    @FXML
    private StackPane nameFilterFieldPlaceholder;

    /**
     * Creates a {@code FilterPanel} with the given {@code ReadOnlyFilterDetails}.
     */
    public FilterPanel(ReadOnlyFilterDetails filterDetails, FilterExecutor filterExecutor) {
        super(FXML);
        this.filterDetails = filterDetails;
        this.filterExecutor = filterExecutor;
        fillInnerParts();
    }

    /**
     * Fills inner placeholders with reusable field components.
     */
    private void fillInnerParts() {
        FilterPanelField nameFilterField = new FilterPanelField(
                "Search by Name",
                "E.g: Alex",
                this::handleNameKeywordsChanged);

        nameFilterFieldPlaceholder.getChildren().setAll(nameFilterField.getRoot());

        filterDetails.getNameKeywords().addListener(
                (SetChangeListener<? super String>) change ->
                        nameFilterField.setKeywords(List.copyOf(filterDetails.getNameKeywords())));

    }

    private void handleNameKeywordsChanged(List<String> nameKeywords) {
        // A linked hash set is used to preserve the order of the keywords as entered by the user
        Set<String> nameKeywordsSet = new LinkedHashSet<>(nameKeywords);
        FilterDetails newFilterDetails = new FilterDetails(filterDetails);
        newFilterDetails.setNameKeywords(nameKeywordsSet);

        try {
            filterExecutor.execute(newFilterDetails);
        } catch (CommandException e) {
            // No-op: MainWindow#executeCommand will handle displaying the error message to the user.
        }
    }
}
