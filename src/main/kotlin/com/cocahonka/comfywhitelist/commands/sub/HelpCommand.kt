package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.config.message.Message
import org.bukkit.command.CommandSender

/**
 * Represents the "help" command, which displays help information about available subcommands.
 *
 * @property commands A list of available [SubCommand] instances.
 */
class HelpCommand(private val commands: List<SubCommand>) : SubCommand {

    override val identifier = "help"
    override val permission = "comfywhitelist.help"
    override val usage = "/comfywl help"

    private val helpMessage by lazy {
        val builder = StringBuilder("\n")
        for (command in commands) {
            builder.append("> ${command.usage}\n")
        }
        builder.deleteCharAt(builder.length - 1)
        builder.toString()
    }

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if(isInvalidUsage(sender) { args.isEmpty() }) return false

        val message = Message.joinWithPrefix(helpMessage)
        sender.sendMessage(message)
        return true
    }

}