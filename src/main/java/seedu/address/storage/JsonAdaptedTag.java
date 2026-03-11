package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagType;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTag {

    private final String tagName;
    private final String type;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedTag(@JsonProperty("tagType") String type,
                          @JsonProperty("tagName") String tagName) {

        this.tagName = tagName;
        this.type = type;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {

        this.type = source.type.name();
        tagName = source.tagName;
    }


    public String getTagName() {
        return tagName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }
        TagType type;
        try {
            type = TagType.valueOf(this.type);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException("Invalid tag type: " + this.type);
        }
        return new Tag(type, tagName);
    }

}
