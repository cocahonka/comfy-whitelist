package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.SubCommand
import org.bukkit.command.CommandSender

/**
 * Displays help information for the plugin commands.
 */
class HelpCommand : SubCommand {

    override val identifier = "help"
    override val permission = "comfywhitelist.help"
    override val usage = "/comfywhitelist help"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        TODO("Not yet implemented")
    }

}