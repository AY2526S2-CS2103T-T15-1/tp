package seedu.address.model.person.attributes.impl;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.model.person.attributes.PersonAttribute;

/**
 * Represents a Demerit record for a Person in the address book.
 * Contains a description and timestamp.
 * Guarantees: immutable; is valid as declared in {@link #isValidDemerit(String)}
 */
public class Demerit extends PersonAttribute {

    public static final String MESSAGE_CONSTRAINTS =
            "Demerit must be in format: DESCRIPTION|YYYY-MM-DD HH:MM (e.g., Late night noise|2024-01-15 23:30)";
    public static final String VALIDATION_REGEX = ".+\\|\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final String description;
    private final LocalDateTime timestamp;

    /**
     * Constructs a {@code Demerit}.
     *
     * @param demerit A valid demerit string in format "description|timestamp".
     */
    public Demerit(String demerit) {
        super(demerit);
        requireNonNull(demerit);
        String[] parts = demerit.split("\\|", 2);
        this.description = parts[0].trim();
        this.timestamp = LocalDateTime.parse(parts[1].trim(), FORMATTER);
    }

    /**
     * Constructs a {@code Demerit} with separate description and timestamp.
     *
     * @param description The demerit description.
     * @param timestamp The time when the demerit was issued.
     */
    public Demerit(String description, LocalDateTime timestamp) {
        super(description + "|" + timestamp.format(FORMATTER));
        requireNonNull(description);
        requireNonNull(timestamp);
        this.description = description;
        this.timestamp = timestamp;
    }

    /**
     * Returns true if a given string is a valid demerit format.
     */
    public static boolean isValidDemerit(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        try {
            String[] parts = test.split("\\|", 2);
            LocalDateTime.parse(parts[1].trim(), FORMATTER);
            return !parts[0].trim().isEmpty();
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Demerit otherDemerit)) {
            return false;
        }

        return description.equals(otherDemerit.description)
                && timestamp.equals(otherDemerit.timestamp);
    }

    @Override
    public int hashCode() {
        return description.hashCode() + timestamp.hashCode();
    }
}
