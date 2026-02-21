package seedu.address.model.person.attributes;

/**
 * Functional interface for validating attribute values.
 */
@FunctionalInterface
public interface AttributeValidator {
    boolean isValidAttribute(String value);
}