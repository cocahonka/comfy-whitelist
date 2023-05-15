package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import com.cocahonka.comfywhitelist.config.message.MessageTagResolvers
import com.cocahonka.comfywhitelist.storage.Storage
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender

/**
 * Represents the "clear" command, which clears the whitelist.
 *
 * @property storage The [Storage] instance to interact with whitelist data.
 */
class ClearCommand(private val storage: Storage) : SubCommand {

    override val identifier = "clear"
    override val permission = "comfywhitelist.clear"
    override val usage = "/comfywl clear"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.isNotEmpty()){
            val message = MessageConfig.invalidUsage
            val messageComponent = MiniMessage.miniMessage().deserialize(
                message,
                MessageTagResolvers.warning,
                MessageTagResolvers.insertUsage(usage),
            )
            sender.sendMessage(messageComponent)
            return false
        }

        val message = MessageConfig.whitelistCleared
        val messageComponent = MiniMessage.miniMessage().deserialize(
            message,
            MessageTagResolvers.remove,
        )
        sender.sendMessage(messageComponent)

        return storage.clear()
    }

}