package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import com.cocahonka.comfywhitelist.config.message.MessageTagResolvers
import com.cocahonka.comfywhitelist.storage.Storage
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender

/**
 * Represents the "remove" command, which removes a player from the whitelist.
 *
 * @property storage The [Storage] instance to interact with whitelist data.
 */
class RemoveCommand(private val storage: Storage) : SubCommand {

    override val identifier = "remove"
    override val permission = "comfywhitelist.remove"
    override val usage = "/comfywl remove <name>"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if(isInvalidUsage(sender) { args.size == 1 }) return false

        val playerName = args[0]
        if (!playerName.matches(SubCommand.playerNameRegex)){
            val message = MessageConfig.invalidPlayerName
            val messageComponent = MiniMessage.miniMessage().deserialize(
                message,
                MessageTagResolvers.warning,
            )
            sender.sendMessage(messageComponent)
            return false
        }

        if(!storage.isPlayerWhitelisted(playerName)) {
            val message = MessageConfig.nonExistentPlayerName
            val messageComponent = MiniMessage.miniMessage().deserialize(
                message,
                MessageTagResolvers.warning,
                MessageTagResolvers.insertName(playerName),
            )
            sender.sendMessage(messageComponent)
            return false
        }

        val message = MessageConfig.playerRemoved
        val messageComponent = MiniMessage.miniMessage().deserialize(
            message,
            MessageTagResolvers.remove,
            MessageTagResolvers.insertName(playerName),
        )
        sender.sendMessage(messageComponent)
        return storage.removePlayer(playerName)
    }

}