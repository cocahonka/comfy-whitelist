package com.cocahonka.comfywhitelist.commands

import com.cocahonka.comfywhitelist.ComfyWhitelist
import com.cocahonka.comfywhitelist.commands.sub.*
import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import com.cocahonka.comfywhitelist.storage.Storage
import net.kyori.adventure.text.Component
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
        const val usage = "/comfywl <command>"
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
       if (args.isNullOrEmpty()) {
           val message = MessageConfig.invalidUsage.replace("%s", usage)
           sender.sendMessage(Component.text(message))
           return false
       }

        val subCommandName = args[0]
        val subCommand = subCommands.find { it.identifier.equals(subCommandName, true) }
        if (subCommand == null) {
            val message = MessageConfig.unknownSubcommand
            sender.sendMessage(Component.text(message))
            return false
        }

        if (!sender.hasPermission(subCommand.permission)){
            val message = MessageConfig.noPermission
            sender.sendMessage(Component.text(message))
            return false
        }

        return subCommand.execute(sender, args.drop(1).toTypedArray())
    }
}