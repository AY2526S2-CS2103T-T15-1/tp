package seedu.address.model.person.attributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.model.person.attributes.impl.Address;
import seedu.address.model.person.attributes.impl.CcaPoints;
import seedu.address.model.person.attributes.impl.Demerit;
import seedu.address.model.person.attributes.impl.Email;
import seedu.address.model.person.attributes.impl.Floor;
import seedu.address.model.person.attributes.impl.Gender;
import seedu.address.model.person.attributes.impl.Name;
import seedu.address.model.person.attributes.impl.Phone;
import seedu.address.model.person.attributes.impl.Room;
import seedu.address.model.person.attributes.impl.Tag;
import seedu.address.model.person.attributes.impl.YearOfStudy;

/**
 * Enumeration of all {@link PersonAttribute} types.
 * <p>
 * Centralizes prefix all constants, properties, and common methods for each attribute.
 * Adding a new attribute requires only adding a new enum entry here and creating the attribute class.
 */
public enum AttributeType {
    // ============= All Person Attributes ====================

    // Compulsory single-value attributes (required = true, multiValue = false)
    NAME("n/", "Name", Name.class, true, false,
            Name::new, Name::isValidName, Name.MESSAGE_CONSTRAINTS),
    PHONE("p/", "Phone", Phone.class, true, false,
            Phone::new, Phone::isValidPhone, Phone.MESSAGE_CONSTRAINTS),
    EMAIL("e/", "Email", Email.class, true, false,
            Email::new, Email::isValidEmail, Email.MESSAGE_CONSTRAINTS),
    YEAR_OF_STUDY("y/", "Year of Study", YearOfStudy.class, false, false,
            YearOfStudy::new, YearOfStudy::isValidYear, YearOfStudy.MESSAGE_CONSTRAINTS),

    // Optional single-value attributes (required = false, multiValue = false)
    ADDRESS("a/", "Address", Address.class, false, false,
            Address::new, Address::isValidAddress, Address.MESSAGE_CONSTRAINTS),
    ROOM("r/", "Room", Room.class, false, false,
            Room::new, Room::isValidRoom, Room.MESSAGE_CONSTRAINTS),
    FLOOR("fl/", "Floor", Floor.class, false, false,
            Floor::new, Floor::isValidFloor, Floor.MESSAGE_CONSTRAINTS),
    GENDER("g/", "Gender", Gender.class, false, false,
            Gender::new, Gender::isValidGender, Gender.MESSAGE_CONSTRAINTS),

    // Optional multi-value attributes (required = false, multiValue = true)
    // e.g., multiple tags, multiple CCA Point Records, multiple Demerit Records
    CCA_POINT_RECORDS("m/", "CCA Point Records", CcaPoints.class, false, true,
            CcaPoints::new, CcaPoints::isValidCcaPoints, CcaPoints.MESSAGE_CONSTRAINTS),
    DEMERIT_REORDS("d/", "Demerits", Demerit.class, false, true,
            Demerit::new, Demerit::isValidDemerit, Demerit.MESSAGE_CONSTRAINTS),
    TAG("t/", "Tag", Tag.class, false, true,
            Tag::new, Tag::isValidTag, Tag.MESSAGE_CONSTRAINTS);

    // ============= ALl {} properties  ====================
    /**
     * The CLI prefix for this attribute type.
     * AKA: How to set this attribute in commands (e.g., "n/" for {@link Name}).
     */
    private final String prefix;

    /**
     * The user-friendly display name for this attribute type.
     */
    private final String displayName;

    /**
     * The class of the PersonAttribute associated with this type.
     */
    private final Class<? extends PersonAttribute> attributeClass;

    /**
     * Whether this attribute type is required for a Person (e.g., Name, Phone, Email, etc. are required).
     */
    private final boolean required;

    /**
     * Whether this attribute type can have multiple values for a single Person
     * (e.g., Tags, CCA Points, Demerits can have multiple entries).
     */
    private final boolean multiValue;

    /**
     * Factory for creating instances of the associated PersonAttribute from a string value.
     * Factor methods must implement {@link AttributeFactory}
     */
    private final AttributeFactory factory;

    /**
     * Validator for checking if a string value is valid for this attribute type.
     * Validator methods must implement {@link AttributeValidator}
     */
    private final AttributeValidator validator;

    /** The message constraints to show when validation fails for this attribute type.
     * Typically defined as a constant in the PersonAttribute class (e.g., {@link Name#MESSAGE_CONSTRAINTS}).
     */
    private final String messageConstraints;

    // =================== Constructor ============================
    AttributeType(String prefix, String displayName, Class<? extends PersonAttribute> attributeClass,
                  boolean required, boolean multiValue, AttributeFactory factory, AttributeValidator validator,
                  String messageConstraints) {
        this.prefix = prefix;
        this.displayName = displayName;
        this.attributeClass = attributeClass;
        this.required = required;
        this.multiValue = multiValue;
        this.factory = factory;
        this.validator = validator;
        this.messageConstraints = messageConstraints;
    }

    // ==================== Getters ====================

    public String getPrefix() {
        return prefix;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Class<? extends PersonAttribute> getAttributeClass() {
        return attributeClass;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isMultiValue() {
        return multiValue;
    }

    public String getMessageConstraints() {
        return messageConstraints;
    }

    // ==================== Factory & Validation ====================

    /**
     * Creates an instance of this attribute type with the given value.
     *
     * @param value The string value for the attribute.
     * @return A new PersonAttribute instance.
     */
    public PersonAttribute create(String value) {
        return factory.create(value);
    }

    /**
     * Validates if the given value is valid for this attribute type.
     *
     * @param value The string value to validate.
     * @return true if valid, false otherwise.
     */
    public boolean isValid(String value) {
        return validator.isValidAttribute(value);
    }

    // ==================== Static Lookups ====================

    /**
     * Finds an AttributeType by its CLI prefix.
     *
     * @param prefix The prefix to search for (e.g., "n/").
     * @return Optional containing the AttributeType if found.
     */
    public static Optional<AttributeType> fromPrefix(String prefix) {
        return Arrays.stream(values())
                .filter(t -> t.prefix.equals(prefix))
                .findFirst();
    }

    /**
     * Returns all required attribute types.
     *
     * @return List of required AttributeTypes.
     */
    public static List<AttributeType> getRequiredTypes() {
        return Arrays.stream(values())
                .filter(AttributeType::isRequired)
                .collect(Collectors.toList());
    }

    /**
     * Returns all optional attribute types.
     *
     * @return List of optional AttributeTypes.
     */
    public static List<AttributeType> getOptionalTypes() {
        return Arrays.stream(values())
                .filter(t -> !t.isRequired())
                .collect(Collectors.toList());
    }

    /**
     * Returns all single-value attribute types.
     *
     * @return List of single-value AttributeTypes.
     */
    public static List<AttributeType> getSingleValueTypes() {
        return Arrays.stream(values())
                .filter(t -> !t.isMultiValue())
                .collect(Collectors.toList());
    }

    /**
     * Returns all multi-value attribute types.
     *
     * @return List of multi-value AttributeTypes.
     */
    public static List<AttributeType> getMultiValueTypes() {
        return Arrays.stream(values())
                .filter(AttributeType::isMultiValue)
                .collect(Collectors.toList());
    }

    /**
     * Returns all prefixes as a list of strings.
     *
     * @return List of all prefixes.
     */
    public static List<String> getAllPrefixes() {
        return Arrays.stream(values())
                .map(AttributeType::getPrefix)
                .collect(Collectors.toList());
    }
}
