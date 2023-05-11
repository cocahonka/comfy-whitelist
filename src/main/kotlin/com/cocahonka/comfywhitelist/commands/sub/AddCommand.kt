package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.storage.Storage
import org.bukkit.command.CommandSender

/**
 * Represents the "add" subcommand, which adds a player to the whitelist.
 *
 * @property storage The [Storage] instance used to interact with the whitelist.
 */
class AddCommand(private val storage: Storage) : SubCommand {

    override val identifier = "add"
    override val permission = "comfywhitelist.add"
    override val usage = "/comfywhitelist add <player_name>"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        TODO("Not yet implemented")
    }

}