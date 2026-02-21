package seedu.address.model.person.attributes;

@FunctionalInterface
public interface AttributeFactory {
    PersonAttribute create(String value);
}
