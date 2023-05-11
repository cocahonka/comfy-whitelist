package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.SubCommand
import org.bukkit.command.CommandSender

/**
 * Reloads the whitelist data from storage.
 */
class ReloadCommand : SubCommand {

    override val identifier = "reload"
    override val permission = "comfywhitelist.reload"
    override val usage = "/comfywl reload"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        TODO("Not yet implemented")
    }

}