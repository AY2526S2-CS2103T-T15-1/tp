package seedu.address.model.person.attributes.impl;

import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.person.attributes.PersonAttribute;

/**
 * Represents a Person's year of study in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidYear(String)}
 */
public class YearOfStudy extends PersonAttribute {

    public static final String MESSAGE_CONSTRAINTS = "Year of study must be a number between 1 and 6";
    public static final int MIN_YEAR = 1;
    public static final int MAX_YEAR = 6;

    private final int year;

    /**
     * Constructs a {@code YearOfStudy}.
     *
     * @param year A valid year of study.
     */
    public YearOfStudy(String year) {
        super(year);
        checkArgument(isValidYear(year), MESSAGE_CONSTRAINTS);
        this.year = Integer.parseInt(year);
    }

    /**
     * Returns true if a given string is a valid year of study.
     */
    public static boolean isValidYear(String test) {
        try {
            int year = Integer.parseInt(test);
            return year >= MIN_YEAR && year <= MAX_YEAR;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof YearOfStudy otherYear)) {
            return false;
        }

        return year == otherYear.year;
    }
}
