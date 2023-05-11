package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.storage.Storage
import org.bukkit.command.CommandSender

/**
 * Represents the "clear" subcommand, which clears the whitelist of all players.
 *
 * @property storage The [Storage] instance used to interact with the whitelist.
 */
class ClearCommand(private val storage: Storage) : SubCommand {

    override val identifier = "clear"
    override val permission = "comfywhitelist.clear"
    override val usage = "/comfywl clear"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        TODO("Not yet implemented")
    }

}