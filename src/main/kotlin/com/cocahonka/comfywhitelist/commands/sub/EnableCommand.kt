package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.SubCommand
import org.bukkit.command.CommandSender

/**
 * Enables the whitelist, restricting access to whitelisted players.
 */
class EnableCommand : SubCommand {

    override val identifier = "on"
    override val permission = "comfywhitelist.on"
    override val usage = "/comfywhitelist on"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        TODO("Not yet implemented")
    }

}