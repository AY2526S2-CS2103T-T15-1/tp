package seedu.address.model.person;

/**
 * Represents a Person's points in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPoints(Integer)}
 */
public class Points {
    private final Integer points;

    public Points(Integer points) {
        this.points = points;
    }

    /**
     * Returns true if a given integer is a valid value for points.
     */
    public boolean isValidPoints(Integer test) {
        return test >= 0;
    }

    @Override
    public String toString() {
        return points.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Points)) {
            return false;
        }

        Points otherPoints = (Points) other;
        return points.equals(otherPoints.points);
    }

    @Override
    public int hashCode() {
        return points.hashCode();
    }
}

