package seedu.address.model.person.attributes;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's points in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPoints(String)}
 */
public class Points extends PersonAttribute {
    public static final String MESSAGE_CONSTRAINTS = "Points can take any positive integer value, and it should not " +
            "be blank";

    private final Integer points;

    private Points(Integer points) {
        super(points.toString());
        this.points = points;
    }

    public Points(String points) {
        super(points);
        checkArgument(isValidPoints(points), MESSAGE_CONSTRAINTS);
        this.points = Integer.parseInt(points);
    }

     /**
     * Returns true if a given Integer is a valid points value.
     */
    /**
     * Returns true if a given String is a valid points value.
     */
    public static boolean isValidPoints(String test) {
        try {
            int points = Integer.parseInt(test);
            return points >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Points otherPoints)) {
            return false;
        }

        return points.equals(otherPoints.points);
    }
}