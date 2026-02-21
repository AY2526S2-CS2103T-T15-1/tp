package seedu.address.storage;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.attributes.AttributeType;
import seedu.address.model.person.attributes.PersonAttribute;
import seedu.address.model.person.attributes.impl.Address;
import seedu.address.model.person.attributes.impl.Email;
import seedu.address.model.person.attributes.impl.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.attributes.impl.Phone;
import seedu.address.model.person.attributes.impl.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("address") String address,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.get(AttributeType.NAME, Name.class).map(n -> n.value).orElse(null);
        phone = source.get(AttributeType.PHONE, Phone.class).map(p -> p.value).orElse(null);
        email = source.get(AttributeType.EMAIL, Email.class).map(e -> e.value).orElse(null);
        address = source.get(AttributeType.ADDRESS, Address.class).map(a -> a.value).orElse(null);
        tags.addAll(source.getMultiAttribute(AttributeType.TAG, Tag.class)
                .stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = tags.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        final Name modelName = validateAndGetName();
        final Phone modelPhone = validateAndGetPhone();
        final Email modelEmail = validateAndGetEmail();
        final Address modelAddress = validateAndGetAddress();

        Map<AttributeType, PersonAttribute> singleAttributes = new EnumMap<>(AttributeType.class);
        singleAttributes.put(AttributeType.NAME, modelName);
        singleAttributes.put(AttributeType.PHONE, modelPhone);
        singleAttributes.put(AttributeType.EMAIL, modelEmail);
        singleAttributes.put(AttributeType.ADDRESS, modelAddress);

        Map<AttributeType, List<PersonAttribute>> multiAttributes = new EnumMap<>(AttributeType.class);
        multiAttributes.put(AttributeType.TAG, new java.util.ArrayList<>(personTags));

        return new Person(singleAttributes, multiAttributes);
    }

    private Address validateAndGetAddress() throws IllegalValueException {
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);
        return modelAddress;
    }

    private Email validateAndGetEmail() throws IllegalValueException {
        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);
        return modelEmail;
    }

    private Name validateAndGetName() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);
        return modelName;
    }

    private Phone validateAndGetPhone() throws IllegalValueException {
        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);
        return modelPhone;
    }

}
