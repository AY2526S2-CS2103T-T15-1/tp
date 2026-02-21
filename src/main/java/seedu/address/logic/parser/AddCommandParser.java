package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.attributes.AttributeType;
import seedu.address.model.person.attributes.PersonAttribute;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        // Tokenize the arguments based on the defined prefixes
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        // Validate that all required prefixes are present and that there are no extraneous arguments
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        // Validate that there are no duplicate prefixes for single-value attributes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
        PersonAttribute name = ParserUtil.parseAttribute(AttributeType.NAME, argMultimap.getValue(PREFIX_NAME).get());
        PersonAttribute phone = ParserUtil.parseAttribute(AttributeType.PHONE, argMultimap.getValue(PREFIX_PHONE).get());
        PersonAttribute email = ParserUtil.parseAttribute(AttributeType.EMAIL, argMultimap.getValue(PREFIX_EMAIL).get());
        PersonAttribute address = ParserUtil.parseAttribute(AttributeType.ADDRESS, argMultimap.getValue(PREFIX_ADDRESS).get());
        List<PersonAttribute> tagList = ParserUtil.parseAttributes(AttributeType.TAG, argMultimap.getAllValues(PREFIX_TAG));

        // Create the singleAttributes and multiAttributes maps for the Person constructor
        Map<AttributeType, PersonAttribute> singleAttributes = new EnumMap<>(AttributeType.class);
        singleAttributes.put(AttributeType.NAME, name);
        singleAttributes.put(AttributeType.PHONE, phone);
        singleAttributes.put(AttributeType.EMAIL, email);
        singleAttributes.put(AttributeType.ADDRESS, address);

        Map<AttributeType, List<PersonAttribute>> multiAttributes = new EnumMap<>(AttributeType.class);
        multiAttributes.put(AttributeType.TAG, tagList);

        Person person = new Person(singleAttributes, multiAttributes);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
