package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.attributes.AttributeType;
import seedu.address.model.person.attributes.PersonAttribute;

/**
 * Represents a Person in the address book.
 * All attribute handling is automated through AttributeType enum.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Attribute storage
    /**
     * singleAttributes is a map that stores single-value attributes (e.g., name, phone, email).
     * Key is the AttributeType, value is the PersonAttribute of that type.
     */
    private final Map<AttributeType, PersonAttribute> singleAttributes;

    /**
     * multiAttributes is a map that stores multi-value attributes (e.g., tags, CCA points, demerits).
     * Key is the AttributeType, value is a list of PersonAttribute of that type.
     */
    private final Map<AttributeType, List<PersonAttribute>> multiAttributes;

    /**
     * Constructs a Person with the given attributes.
     * Required fields must be present in singleAttributes.
     * Multi-value attributes are validated for duplicates.
     *
     * @throws IllegalArgumentException if required fields are missing or duplicates exist in multi-value attributes.
     */
    public Person(Map<AttributeType, PersonAttribute> singleAttributes,
                  Map<AttributeType, List<PersonAttribute>> multiAttributes) {
        requireAllNonNull(singleAttributes, multiAttributes);

        // Validate required fields
//        for (AttributeType type : AttributeType.getRequiredTypes()) {
//            if (!singleAttributes.containsKey(type)) {
//                throw new IllegalArgumentException("Missing required field: " + type.getDisplayName());
//            }
//        }

        this.singleAttributes = new EnumMap<>(singleAttributes);
        this.multiAttributes = new EnumMap<>(AttributeType.class);

        // Process multi-value attributes with duplicate check
        for (Map.Entry<AttributeType, List<PersonAttribute>> entry : multiAttributes.entrySet()) {
            AttributeType type = entry.getKey();
            List<PersonAttribute> values = entry.getValue();

            // Check for duplicates using equals()
            validateNoDuplicates(type, values);

            this.multiAttributes.put(type, new ArrayList<>(values));
        }
    }

    /**
     * Validates that there are no duplicate attributes in the list.
     *
     * @throws IllegalArgumentException if duplicates are found.
     */
    private void validateNoDuplicates(AttributeType type, List<PersonAttribute> attributes) {
        Set<PersonAttribute> seen = new HashSet<>();
        for (PersonAttribute attr : attributes) {
            if (!seen.add(attr)) {
                throw new IllegalArgumentException(
                        "Duplicate " + type.getDisplayName() + " found: " + attr.value);
            }
        }
    }

    // ==================== Generic Attribute Access ====================

    /**
     * Returns the attribute of the given type, if present.
     */
    public Optional<PersonAttribute> getAttribute(AttributeType type) {
        return Optional.ofNullable(singleAttributes.get(type));
    }

    /**
     * Returns the attribute of the given type, if present, as a specific class.
     */
    public <T extends PersonAttribute> Optional<T> get(AttributeType type, Class<T> clazz) {
        return getAttribute(type).filter(clazz::isInstance).map(a -> (T) a);
    }

    /**
     * Returns the attribute of the given type, if present, as PersonAttribute.
     */
    public Optional<PersonAttribute> get(AttributeType type) {
        return getAttribute(type);
    }

    /**
     * Sets or replaces a single-value attribute.
     * Returns a new Person instance with the updated attribute.
     */
    public Person set(AttributeType type, PersonAttribute attribute) {
        Map<AttributeType, PersonAttribute> newSingle = new EnumMap<>(singleAttributes);
        newSingle.put(type, attribute);
        return new Person(newSingle, multiAttributes);
    }

    /**
     * Sets or replaces a multi-value attribute.
     * Returns a new Person instance with the updated multi-value attribute.
     */
    public Person setMulti(AttributeType type, List<PersonAttribute> attributes) {
        Map<AttributeType, List<PersonAttribute>> newMulti = new EnumMap<>(multiAttributes);
        newMulti.put(type, new ArrayList<>(attributes));
        return new Person(singleAttributes, newMulti);
    }

    /**
     * Returns the list of multi-value attributes of the given type.
     */
    public List<PersonAttribute> getMultiAttribute(AttributeType type) {
        return Collections.unmodifiableList(multiAttributes.getOrDefault(type, Collections.emptyList()));
    }

    /**
     * Returns the list of multi-value attributes of the given type, cast to the expected class.
     */
    @SuppressWarnings("unchecked")
    public <T extends PersonAttribute> List<T> getMultiAttribute(AttributeType type, Class<T> clazz) {
        return getMultiAttribute(type).stream()
                .filter(clazz::isInstance)
                .map(a -> (T) a)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this person has the given attribute type.
     */
    public boolean hasAttribute(AttributeType type) {
        return singleAttributes.containsKey(type)
                || (multiAttributes.containsKey(type) && !multiAttributes.get(type).isEmpty());
    }

    /**
     * Returns all single-value attribute types this person has.
     */
    public Set<AttributeType> getAttributeTypes() {
        return Collections.unmodifiableSet(singleAttributes.keySet());
    }

    /**
     * Returns an unmodifiable view of the single-value attributes map.
     */
    public Map<AttributeType, PersonAttribute> getSingleAttributes() {
        return Collections.unmodifiableMap(singleAttributes);
    }

    /**
     * Returns an unmodifiable view of the multi-value attributes map.
     */
    public Map<AttributeType, List<PersonAttribute>> getMultiAttributes() {
        return Collections.unmodifiableMap(multiAttributes);
    }


    // ==================== Identity ====================

    /**
     * Returns true if both persons have the same NAME attribute.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        if (otherPerson == null) {
            return false;
        }

        return getAttribute(AttributeType.NAME).equals(otherPerson.getAttribute(AttributeType.NAME));
    }

    // ==================== Auto-generated equals/hashCode/toString ====================

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person otherPerson)) {
            return false;
        }

        return singleAttributes.equals(otherPerson.singleAttributes)
                && multiAttributes.equals(otherPerson.multiAttributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(singleAttributes, multiAttributes);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append("{");

        // Single-value attributes - auto iterate through all types
        boolean first = true;
        for (AttributeType type : AttributeType.getSingleValueTypes()) {
            Optional<PersonAttribute> attr = getAttribute(type);
            if (attr.isPresent()) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(type.name().toLowerCase()).append("=").append(attr.get().value);
                first = false;
            }
        }

        // Multi-value attributes - auto iterate through all types
        for (AttributeType type : AttributeType.getMultiValueTypes()) {
            List<PersonAttribute> attrs = getMultiAttribute(type);
            if (!attrs.isEmpty()) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(type.name().toLowerCase()).append("=[");
                sb.append(attrs.stream().map(a -> a.value).collect(Collectors.joining(", ")));
                sb.append("]");
                first = false;
            }
        }
        sb.append("}");
        return sb.toString();
    }

    // ==================== Display Formatting ====================

    /**
     * Returns a formatted string that includes all person attributes' values.
     */
    public String toDisplayString() {
        StringBuilder sb = new StringBuilder();

        // Single-value attributes
        for (AttributeType type : AttributeType.getSingleValueTypes()) {
            getAttribute(type).ifPresent(attr ->
                    sb.append(type.getDisplayName()).append(": ").append(attr.value).append("\n")
            );
        }

        // Multi-value attributes - show values (including tags)
        for (AttributeType type : AttributeType.getMultiValueTypes()) {
            List<PersonAttribute> attrs = getMultiAttribute(type);
            if (!attrs.isEmpty()) {
                sb.append(type.getDisplayName()).append(": ");
                sb.append(attrs.stream().map(a -> a.value).collect(Collectors.joining(", ")));
                sb.append("\n");
            }
        }

        return sb.toString().trim();
    }
}
