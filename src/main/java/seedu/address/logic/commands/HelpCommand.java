package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Opens the HallLedger help window.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens the HallLedger help window.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_HELP_SUCCESS = "Opened HallLedger help window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_HELP_SUCCESS, true, false);
    }
}
