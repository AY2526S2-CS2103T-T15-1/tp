package seedu.address.model.person.attributes.impl;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.person.attributes.PersonAttribute;

/**
 * Represents a Tag attribute for a Person in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTag(String)}
 */
public class Tag extends PersonAttribute {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        super(tagName);
        requireNonNull(tagName);
        checkArgument(isValidTag(tagName), MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTag(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tag otherTag)) {
            return false;
        }

        return value.equals(otherTag.value);
    }

    @Override
    public String toString() {
        return '[' + value + ']';
    }
}
