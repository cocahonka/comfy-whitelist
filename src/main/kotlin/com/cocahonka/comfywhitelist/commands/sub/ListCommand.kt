package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import com.cocahonka.comfywhitelist.config.message.MessageTagResolvers
import com.cocahonka.comfywhitelist.storage.Storage
import net.kyori.adventure.text.minimessage.MiniMessage
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
        if(isInvalidUsage(sender) { args.isEmpty() }) return false

        val playerNameList = storage.getAllWhitelistedPlayers()

        val messageComponent = if (playerNameList.isEmpty()) {
            val message = MessageConfig.emptyWhitelistedPlayersList
            MiniMessage.miniMessage().deserialize(
                message,
                MessageTagResolvers.off,
            )
        } else {
            val message = MessageConfig.whitelistedPlayersList
            MiniMessage.miniMessage().deserialize(
                message,
                MessageTagResolvers.success,
                MessageTagResolvers.insertPlayers(playerNameList),
            )
        }
        sender.sendMessage(messageComponent)

        return true
    }

}