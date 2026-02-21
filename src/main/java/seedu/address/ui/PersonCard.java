package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.attributes.AttributeType;
import seedu.address.model.person.attributes.impl.Address;
import seedu.address.model.person.attributes.impl.Email;
import seedu.address.model.person.attributes.impl.Name;
import seedu.address.model.person.attributes.impl.Phone;
import seedu.address.model.person.attributes.impl.Tag;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.get(AttributeType.NAME, Name.class).map(n -> n.value).orElse(""));
        phone.setText(person.get(AttributeType.PHONE, Phone.class).map(p -> p.value).orElse(""));
        address.setText(person.get(AttributeType.ADDRESS, Address.class).map(a -> a.value).orElse(""));
        email.setText(person.get(AttributeType.EMAIL, Email.class).map(e -> e.value).orElse(""));
        person.getMultiAttribute(AttributeType.TAG, Tag.class)
            .stream()
            .sorted(Comparator.comparing(tag -> tag.value))
            .forEach(tag -> tags.getChildren().add(new Label(tag.value)));
    }
}
