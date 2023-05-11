package com.cocahonka.comfywhitelist.commands

import com.cocahonka.comfywhitelist.ComfyWhitelist
import com.cocahonka.comfywhitelist.commands.sub.*
import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.storage.Storage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

/**
 * Handles the execution of the main command and delegates to the appropriate SubCommand.
 *
 * @property storage The storage instance used to store and manage whitelist data.
 */
class CommandHandler(
    storage: Storage,
    generalConfig: GeneralConfig,
    plugin: ComfyWhitelist,
) : CommandExecutor {

    companion object {
        const val identifier = "comfywhitelist"
    }

    private val subCommands: List<SubCommand>

    init {
        val commands = listOf(
            AddCommand(storage),
            RemoveCommand(storage),
            ClearCommand(storage),
            ListCommand(storage),
            EnableCommand(generalConfig),
            DisableCommand(generalConfig),
            ReloadCommand(plugin),
        )
        val helpCommand = HelpCommand(commands)
        subCommands = commands + helpCommand
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        TODO("Not yet implemented")
    }
}