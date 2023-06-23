package com.cocahonka.comfywhitelist.commands

import com.cocahonka.comfywhitelist.ComfyWhitelist
import com.cocahonka.comfywhitelist.LegacyUtils.sendMessage
import com.cocahonka.comfywhitelist.api.Storage
import com.cocahonka.comfywhitelist.commands.sub.*
import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import com.cocahonka.comfywhitelist.config.message.MessageFormat
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender

/**
 * Handles subcommands and their execution for the ComfyWhitelist plugin.
 *
 * @param storage The [Storage] instance to interact with whitelist data.
 * @param generalConfig The [GeneralConfig] instance to manage plugin configuration.
 * @param plugin The [ComfyWhitelist] plugin instance.
 */
class CommandHandler(
    storage: Storage,
    generalConfig: GeneralConfig,
    plugin: ComfyWhitelist,
) : CommandExecutor {

    companion object {
        const val identifier = "comfywhitelist"
        const val usage = "/comfywl <command>"
        val aliases = listOf("comfywl")
    }

    val subCommands: List<SubCommand>

    init {
        val commands = listOf(
            AddCommand(storage),
            RemoveCommand(storage),
            ListCommand(storage),
            StatusCommand(),
            EnableCommand(generalConfig),
            DisableCommand(generalConfig),
            ClearCommand(storage),
            ReloadCommand(plugin),
        )
        val helpCommand = HelpCommand(commands)
        subCommands = commands + helpCommand
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (args.isNullOrEmpty()) {
            val replacementConfig = MessageFormat.ConfigBuilders.usageReplacementConfigBuilder(usage)
            val message = MessageConfig.invalidUsage.replaceText(replacementConfig)
            sender.sendMessage(message)
            return false
        }

        val subCommandName = args[0]
        val subCommand = subCommands.find { it.identifier.equals(subCommandName, true) }
        if (subCommand == null) {
            sender.sendMessage(MessageConfig.unknownSubcommand)
            return false
        }

        if (sender !is ConsoleCommandSender && !sender.hasPermission(subCommand.permission)) {
            sender.sendMessage(MessageConfig.noPermission)
            return false
        }

        return subCommand.execute(sender, args.drop(1).toTypedArray())
    }
}