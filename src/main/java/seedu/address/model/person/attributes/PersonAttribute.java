package seedu.address.model.person.attributes;

/**
 * Common abstraction for all person attributes.
 * <p>
 * Attributes are guaranteed to be immutable and validated on construction.
 */
public abstract class PersonAttribute {

    /**
     * Enforces string representation of all attributes for Json serialization.
     */
    public final String value;

    /**
     * Constructs a {@code PersonAttribute}.
     *
     * @param value A valid attribute value in {@code String}.
     */
    public PersonAttribute(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public abstract boolean equals(Object other);
}
