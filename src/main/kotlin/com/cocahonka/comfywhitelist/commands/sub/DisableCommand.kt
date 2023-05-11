package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.SubCommand
import org.bukkit.command.CommandSender

/**
 * Disables the whitelist, allowing all players to join.
 */
class DisableCommand : SubCommand {

    override val identifier = "off"
    override val permission = "comfywhitelist.off"
    override val usage = "/comfywhitelist off"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        TODO("Not yet implemented")
    }

}