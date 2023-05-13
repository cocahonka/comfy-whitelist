package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import com.cocahonka.comfywhitelist.storage.Storage
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender

/**
 * Represents the "remove" subcommand, which removes a player from the whitelist.
 *
 * @property storage The [Storage] instance used to interact with the whitelist.
 */
class RemoveCommand(private val storage: Storage) : SubCommand {

    override val identifier = "remove"
    override val permission = "comfywhitelist.remove"
    override val usage = "/comfywl remove <name>"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.size != 1){
            val message = MessageConfig.invalidUsage.replace("%s", usage)
            sender.sendMessage(Component.text(message))
            return false
        }

        val playerName = args[0]
        if (!playerName.matches(SubCommand.playerNameRegex)){
            val message = MessageConfig.invalidPlayerName
            sender.sendMessage(Component.text(message))
            return false
        }

        if(!storage.isPlayerWhitelisted(playerName)) {
            val message = MessageConfig.nonExistentPlayerName.replace("%s", playerName)
            sender.sendMessage(Component.text(message))
            return false
        }

        val message = MessageConfig.playerRemoved.replace("%s", playerName)
        sender.sendMessage(Component.text(message))
        return storage.removePlayer(playerName)
    }

}