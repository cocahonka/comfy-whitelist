package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.CommandHandler
import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import com.cocahonka.comfywhitelist.config.message.MessageTagResolvers
import com.cocahonka.comfywhitelist.storage.Storage
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender

/**
 * Represents the "add" command, which adds a player to the whitelist.
 *
 * @property storage The [Storage] instance to interact with whitelist data.
 */
class AddCommand(private val storage: Storage) : SubCommand {

    override val identifier = "add"
    override val permission = "comfywhitelist.add"
    override val usage = "/comfywl add <name>"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.size != 1){
            val message = MessageConfig.invalidUsage
            val messageComponent = MiniMessage.miniMessage().deserialize(
                message,
                MessageTagResolvers.warning,
                MessageTagResolvers.insertUsage(usage),
            )
            sender.sendMessage(messageComponent)
            return false
        }

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

        val message = MessageConfig.playerAdded
        val messageComponent = MiniMessage.miniMessage().deserialize(
            message,
            MessageTagResolvers.success,
            MessageTagResolvers.insertName(playerName),
        )
        sender.sendMessage(messageComponent)
        return storage.addPlayer(playerName)
    }

}