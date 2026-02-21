package seedu.address.model.person.attributes.impl;

import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.person.attributes.PersonAttribute;

/**
 * Represents a Person's room number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRoom(String)}
 */
public class Room extends PersonAttribute {

    public static final String MESSAGE_CONSTRAINTS = "Room must be 3 digits followed by a letter (e.g., 101A, 215B)";
    public static final String VALIDATION_REGEX = "\\d{3}[A-Za-z]";

    /**
     * Constructs a {@code Room}.
     *
     * @param room A valid room number.
     */
    public Room(String room) {
        super(room.toUpperCase());
        checkArgument(isValidRoom(room), MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns true if a given string is a valid room number.
     */
    public static boolean isValidRoom(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Room otherRoom)) {
            return false;
        }

        return value.equals(otherRoom.value);
    }
}
