package com.cocahonka.comfywhitelist.commands

import com.cocahonka.comfywhitelist.commands.sub.AddCommand
import com.cocahonka.comfywhitelist.commands.sub.RemoveCommand
import com.cocahonka.comfywhitelist.storage.Storage
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

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
                    val whitelistedPlayers = storage.getAllWhitelistedPlayers()
                    return whitelistedPlayers.filter { it.startsWith(subCommandParam, ignoreCase = true) }.toMutableList()
                }
            }
        }

        return mutableListOf()
    }
}
