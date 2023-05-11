package com.cocahonka.comfywhitelist.commands

import com.cocahonka.comfywhitelist.storage.Storage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

/**
 * Handles the execution of the main command and delegates to the appropriate SubCommand.
 *
 * @property storage The storage instance used to store and manage whitelist data.
 */
class CommandHandler(private val storage: Storage) : CommandExecutor {

    companion object {
        const val identifier = "comfywhitelist"
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        TODO("Not yet implemented")
    }
}