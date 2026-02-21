package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.attributes.AttributeType;
import seedu.address.model.person.attributes.impl.Tag;
import seedu.address.model.person.attributes.PersonAttribute;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Generic method to parse any attribute type defined in {@link AttributeType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param type The attribute type to parse.
     * @param value The string value to parse.
     * @return A new PersonAttribute instance of the appropriate type.
     * @throws ParseException if the given value is invalid for the attribute type.
     */
    public static PersonAttribute parseAttribute(AttributeType type, String value) throws ParseException {
        requireNonNull(type);
        requireNonNull(value);
        String trimmed = value.trim();
        if (!type.isValid(trimmed)) {
            throw new ParseException(type.getMessageConstraints());
        }
        return type.create(trimmed);
    }

    /**
     * Parses multiple values for a multi-value attribute type.
     *
     * @param type The attribute type to parse.
     * @param values The collection of string values to parse.
     * @return A list of parsed attributes.
     * @throws ParseException if any value is invalid.
     */
    public static List<PersonAttribute> parseAttributes(AttributeType type, Collection<String> values) throws ParseException {
        requireNonNull(type);
        requireNonNull(values);
        List<PersonAttribute> result = new ArrayList<>();
        for (String value : values) {
            result.add(parseAttribute(type, value));
        }
        return result;
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTag(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
