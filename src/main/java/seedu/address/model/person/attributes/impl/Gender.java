package seedu.address.model.person.attributes.impl;

import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.person.attributes.PersonAttribute;

/**
 * Represents a Person's gender in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender extends PersonAttribute {

    public static final String MESSAGE_CONSTRAINTS = "Gender must be MALE, FEMALE, or OTHER (case-insensitive)";

    /**
     * Valid gender values.
     */
    public enum GenderType {
        MALE, FEMALE, OTHER
    }

    private final GenderType genderType;

    /**
     * Constructs a {@code Gender}.
     *
     * @param gender A valid gender string.
     */
    public Gender(String gender) {
        super(gender.toUpperCase());
        checkArgument(isValidGender(gender), MESSAGE_CONSTRAINTS);
        this.genderType = GenderType.valueOf(gender.toUpperCase());
    }

    /**
     * Returns true if a given string is a valid gender.
     */
    public static boolean isValidGender(String test) {
        try {
            GenderType.valueOf(test.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public GenderType getGenderType() {
        return genderType;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Gender otherGender)) {
            return false;
        }

        return genderType.equals(otherGender.genderType);
    }
}
