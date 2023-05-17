package com.cocahonka.comfywhitelist.commands

import com.cocahonka.comfywhitelist.api.Storage
import com.cocahonka.comfywhitelist.commands.sub.AddCommand
import com.cocahonka.comfywhitelist.commands.sub.RemoveCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

/**
 * This class is responsible for providing tab completion suggestions for the plugin's commands.
 *
 * It suggests the command identifiers as the first argument, and for specific commands ("add" and "remove"),
 * it suggests the online player names or the whitelisted player names respectively.
 *
 * @param storage The [Storage] instance that contains the whitelist data.
 * @param subCommands The list of [SubCommand] instances that the plugin can execute.
 */
class CommandTabCompleter(private val storage: Storage, private val subCommands: List<SubCommand>) : TabCompleter {

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        if (args.size == 1) {
            val subCommandIdentifier = args[0]
            return subCommands.map { it.identifier }.filter { it.startsWith(subCommandIdentifier, ignoreCase = true) }.toMutableList()
        }

        else if (args.size == 2) {
            val subCommandIdentifier = args[0]
            val subCommandParam = args[1]

            val addCommand = subCommands.find { it is AddCommand }
            val removeCommand = subCommands.find {it is RemoveCommand}
            when (subCommandIdentifier.lowercase()) {
                addCommand?.identifier -> {
                    val onlinePlayers = sender.server.onlinePlayers.map { it.name }
                    return onlinePlayers.filter { it.startsWith(subCommandParam, ignoreCase = true) }.toMutableList()
                }
                removeCommand?.identifier -> {
                    val whitelistedPlayers = storage.allWhitelistedPlayers
                    return whitelistedPlayers.filter { it.startsWith(subCommandParam, ignoreCase = true) }.toMutableList()
                }
            }
        }

        return mutableListOf()
    }
}
