package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.api.Storage
import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender

/**
 * Represents the "list" command, which lists all players in the whitelist.
 *
 * @property storage The [Storage] instance to interact with whitelist data.
 */
class ListCommand(private val storage: Storage) : SubCommand {

    override val identifier = "list"
    override val permission = "comfywhitelist.list"
    override val usage = "/comfywl list"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.isNotEmpty()) {
            val message = MessageConfig.invalidUsage.replace("%s", usage)
            sender.sendMessage(Component.text(message))
            return false
        }

        val playerNameList = storage.getAllWhitelistedPlayers()

        val message = if (playerNameList.isEmpty()) {
            MessageConfig.emptyWhitelistedPlayersList
        } else {
            MessageConfig.whitelistedPlayersList
                .replace("%s", playerNameList.joinToString())
        }

        sender.sendMessage(Component.text(message))
        return true
    }

}