package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.storage.Storage
import org.bukkit.command.CommandSender

/**
 * Represents the "remove" subcommand, which removes a player from the whitelist.
 *
 * @property storage The [Storage] instance used to interact with the whitelist.
 */
class RemoveCommand(private val storage: Storage) : SubCommand {

    override val identifier = "remove"
    override val permission = "comfywhitelist.remove"
    override val usage = "/comfywhitelist remove <player_name>"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        TODO("Not yet implemented")
    }

}