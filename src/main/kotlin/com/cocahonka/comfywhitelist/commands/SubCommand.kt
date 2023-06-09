package com.cocahonka.comfywhitelist.commands

import com.cocahonka.comfywhitelist.LegacyUtils.sendMessage
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import com.cocahonka.comfywhitelist.config.message.MessageFormat
import org.bukkit.command.CommandSender

/**
 * Represents a subcommand within the plugin.
 *
 * Implement this interface to create a new subcommand that can be executed by a [CommandSender]
 * and has its own [identifier], [permission], and [usage] information.
 */
interface SubCommand {
    /**
     * The identifier of the subcommand, used to identify and execute the command.
     */
    val identifier: String

    /**
     * The permission required by the [CommandSender] to execute this subcommand.
     * The subcommand will only be executed if the sender has the required permission.
     */
    val permission: String

    /**
     * A string describing the correct usage of the subcommand. This should include
     * any arguments or flags the subcommand may require or support.
     */
    val usage: String

    /**
     * Executes the subcommand with the provided [sender] and [args].
     *
     * @param sender The [CommandSender] who executed the command.
     * @param args An array of arguments passed to the command.
     * @return true if the command executed successfully, false otherwise.
     */
    fun execute(sender: CommandSender, args: Array<String>): Boolean

    /**
     * Checks the validity of the subcommand usage and notifies the [sender] if it's invalid.
     *
     * @param sender The [CommandSender] who executed the command.
     * @param expected A lambda that returns true if the subcommand usage is valid.
     * @return true if the usage is invalid, false otherwise.
     */
    fun isInvalidUsage(sender: CommandSender, expected: () -> Boolean): Boolean {
        if (!expected()){
            val replacementConfig = MessageFormat.ConfigBuilders.usageReplacementConfigBuilder(usage)
            val message = MessageConfig.invalidUsage.replaceText(replacementConfig)
            sender.sendMessage(message)
            return true
        }
        return false
    }

    companion object{

        /**
         * A regex pattern to match valid player names.
         */
        val playerNameRegex = Regex("""^[a-zA-Z0-9_]+$""")
    }
}