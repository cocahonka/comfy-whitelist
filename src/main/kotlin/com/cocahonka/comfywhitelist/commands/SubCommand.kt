package com.cocahonka.comfywhitelist.commands

import org.bukkit.command.CommandSender

/**
 * Represents a subcommand within the plugin.
 *
 * Implement this interface to create a new subcommand that can be executed by a `CommandSender`
 * and has its own name, permission, and usage information.
 */
interface SubCommand {
    /**
     * The name of the subcommand, used to identify and execute the command.
     */
    val identifier: String

    /**
     * The permission required by the `CommandSender` to execute this subcommand.
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

    companion object{
        val playerNameRegex = Regex("""^[a-zA-Z0-9_]+$""")
    }
}