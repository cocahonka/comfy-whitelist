package com.cocahonka.comfywhitelist.commands

import com.cocahonka.comfywhitelist.config.message.MessageConfig
import com.cocahonka.comfywhitelist.config.message.MessageTagResolvers
import net.kyori.adventure.text.minimessage.MiniMessage
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

    fun isInvalidUsage(sender: CommandSender, expected: () -> Boolean): Boolean {
        if (!expected()){
            val message = MessageConfig.invalidUsage
            val messageComponent = MiniMessage.miniMessage().deserialize(
                message,
                MessageTagResolvers.warning,
                MessageTagResolvers.insertUsage(usage),
            )
            sender.sendMessage(messageComponent)
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