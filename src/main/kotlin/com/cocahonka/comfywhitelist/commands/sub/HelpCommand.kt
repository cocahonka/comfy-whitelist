package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.ComfyWhitelist
import com.cocahonka.comfywhitelist.commands.SubCommand
import org.bukkit.command.CommandSender

/**
 * Displays help information for the plugin commands.
 */
class HelpCommand(private val commands: List<SubCommand>) : SubCommand {

    override val identifier = "help"
    override val permission = "comfywhitelist.help"
    override val usage = "/comfywl help"

    private val helpMessage by lazy {
        val builder = StringBuilder(ComfyWhitelist.DISPLAY_NAME + " >")
        for (command in commands) {
            builder.append("> ${command.usage}\n")
        }
        builder.deleteCharAt(builder.length - 1)
        builder.toString()
    }

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        TODO("Not yet implemented")
    }

}