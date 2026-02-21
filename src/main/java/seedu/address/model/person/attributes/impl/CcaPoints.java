package seedu.address.model.person.attributes.impl;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.model.person.attributes.PersonAttribute;

/**
 * Represents a CcaPoints record for a Person in the address book.
 * Contains a description and timestamp.
 * Guarantees: immutable; is valid as declared in {@link #isValidCcaPoints(String)}
 */
public class CcaPoints extends PersonAttribute {

    public static final String MESSAGE_CONSTRAINTS =
            "CcaPoints must be in format: DESCRIPTION|YYYY-MM-DD HH:MM (e.g., Helped organize event|2024-01-15 14:30)";
    public static final String VALIDATION_REGEX = ".+\\|\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final String description;
    private final LocalDateTime timestamp;

    /**
     * Constructs a {@code CcaPoints}.
     *
     * @param ccaPoints A valid CCA Point Records string in format "description|timestamp".
     */
    public CcaPoints(String ccaPoints) {
        super(ccaPoints);
        requireNonNull(ccaPoints);
        String[] parts = ccaPoints.split("\\|", 2);
        this.description = parts[0].trim();
        this.timestamp = LocalDateTime.parse(parts[1].trim(), FORMATTER);
    }

    /**
     * Constructs a {@code CcaPoints} with separate description and timestamp.
     *
     * @param description The CCA Point Records description.
     * @param timestamp The time when the CCA Point Records was awarded.
     */
    public CcaPoints(String description, LocalDateTime timestamp) {
        super(description + "|" + timestamp.format(FORMATTER));
        requireNonNull(description);
        requireNonNull(timestamp);
        this.description = description;
        this.timestamp = timestamp;
    }

    /**
     * Returns true if a given string is a valid CCA Point Records format.
     */
    public static boolean isValidCcaPoints(String test) {
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

        if (!(other instanceof CcaPoints otherCcaPoints)) {
            return false;
        }

        return description.equals(otherCcaPoints.description)
                && timestamp.equals(otherCcaPoints.timestamp);
    }

    @Override
    public int hashCode() {
        return description.hashCode() + timestamp.hashCode();
    }
}
