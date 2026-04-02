package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's room number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRoomNumber(String)}
 */
public class RoomNumber {
    public static final String MESSAGE_CONSTRAINTS = "Room numbers should only contain alphanumeric characters, "
            + "with one or two digits followed by a single alphabet (no space). \n Example: 15R";

    /*
     * The first character of the room number must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^\\d{1,2}[A-Za-z]";

    public final String value;

    /**
     * Constructs an {@code RoomNumber}.
     *
     * @param roomNumber A valid room number.
     */
    public RoomNumber(String roomNumber) {
        requireNonNull(roomNumber);
        checkArgument(isValidRoomNumber(roomNumber), MESSAGE_CONSTRAINTS);

        //Remove leading 0s if any by converting the digits to an integer before concatenating back with the alphabet
        int floor = Integer.parseInt(roomNumber.substring(0, roomNumber.length() - 1));
        String alphabet = roomNumber.substring(roomNumber.length() - 1);
        value = floor + alphabet.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid room number.
     */
    public static boolean isValidRoomNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RoomNumber)) {
            return false;
        }

        RoomNumber otherRoomNumber = (RoomNumber) other;
        return value.equals(otherRoomNumber.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
