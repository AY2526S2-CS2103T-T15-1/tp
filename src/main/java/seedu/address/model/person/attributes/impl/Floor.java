package seedu.address.model.person.attributes.impl;

import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.person.attributes.PersonAttribute;

/**
 * Represents a Person's floor number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFloor(String)}
 */
public class Floor extends PersonAttribute {

    public static final String MESSAGE_CONSTRAINTS = "Floor must be a number between 1 and 19";
    public static final int MIN_FLOOR = 1;
    public static final int MAX_FLOOR = 19;

    private final int floorNumber;

    /**
     * Constructs a {@code Floor}.
     *
     * @param floor A valid floor number.
     */
    public Floor(String floor) {
        super(floor);
        checkArgument(isValidFloor(floor), MESSAGE_CONSTRAINTS);
        this.floorNumber = Integer.parseInt(floor);
    }

    /**
     * Returns true if a given string is a valid floor number.
     */
    public static boolean isValidFloor(String test) {
        try {
            int floor = Integer.parseInt(test);
            return floor >= MIN_FLOOR && floor <= MAX_FLOOR;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Floor otherFloor)) {
            return false;
        }

        return floorNumber == otherFloor.floorNumber;
    }
}
