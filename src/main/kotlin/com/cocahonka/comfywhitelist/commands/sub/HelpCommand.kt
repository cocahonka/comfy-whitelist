package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.ComfyWhitelist
import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import net.kyori.adventure.text.Component
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
        val builder = StringBuilder(ComfyWhitelist.DISPLAY_NAME + " >\n")
        for (command in commands) {
            builder.append("> ${command.usage}\n")
        }
        builder.deleteCharAt(builder.length - 1)
        builder.toString()
    }

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.isNotEmpty()) {
            val message = MessageConfig.invalidUsage.replace("%s", usage)
            sender.sendMessage(Component.text(message))
            return false
        }

        sender.sendMessage(Component.text(helpMessage))
        return true
    }

}