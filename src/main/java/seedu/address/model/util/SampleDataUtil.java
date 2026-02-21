package seedu.address.model.util;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.attributes.AttributeType;
import seedu.address.model.person.attributes.PersonAttribute;
import seedu.address.model.person.attributes.impl.Address;
import seedu.address.model.person.attributes.impl.Email;
import seedu.address.model.person.attributes.impl.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.attributes.impl.Phone;
import seedu.address.model.person.attributes.impl.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            createPerson("Alex Yeoh", "87438807", "alexyeoh@example.com", "Blk 30 Geylang Street 29, #06-40", getTagSet("friends")),
            createPerson("Bernice Yu", "99272758", "berniceyu@example.com", "Blk 30 Lorong 3 Serangoon Gardens, #07-18", getTagSet("colleagues", "friends")),
            createPerson("Charlotte Oliveiro", "93210283", "charlotte@example.com", "Blk 11 Ang Mo Kio Street 74, #11-04", getTagSet("neighbours")),
            createPerson("David Li", "91031282", "lidavid@example.com", "Blk 436 Serangoon Gardens Street 26, #16-43", getTagSet("family")),
            createPerson("Irfan Ibrahim", "92492021", "irfan@example.com", "Blk 47 Tampines Street 20, #17-35", getTagSet("classmates")),
            createPerson("Roy Balakrishnan", "92624417", "royb@example.com", "Blk 45 Aljunied Street 85, #11-31", getTagSet("colleagues"))
        };
    }

    private static Person createPerson(String name, String phone, String email, String address, Set<Tag> tags) {
        Map<AttributeType, PersonAttribute> singleAttributes = new EnumMap<>(AttributeType.class);
        singleAttributes.put(AttributeType.NAME, new Name(name));
        singleAttributes.put(AttributeType.PHONE, new Phone(phone));
        singleAttributes.put(AttributeType.EMAIL, new Email(email));
        singleAttributes.put(AttributeType.ADDRESS, new Address(address));

        Map<AttributeType, List<PersonAttribute>> multiAttributes = new EnumMap<>(AttributeType.class);
        multiAttributes.put(AttributeType.TAG, tags.stream().map(t -> (PersonAttribute) t).toList());

        return new Person(singleAttributes, multiAttributes);
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
