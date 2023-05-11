package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.storage.Storage
import org.bukkit.command.CommandSender

/**
 * Represents the "list" subcommand, which lists all players on the whitelist.
 *
 * @property storage The [Storage] instance used to interact with the whitelist.
 */
class ListCommand(private val storage: Storage) : SubCommand {

    override val identifier = "list"
    override val permission = "comfywhitelist.list"
    override val usage = "/comfywl list"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        TODO("Not yet implemented")
    }

}