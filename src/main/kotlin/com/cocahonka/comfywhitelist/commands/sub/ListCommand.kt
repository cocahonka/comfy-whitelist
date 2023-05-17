package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.api.Storage
import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import com.cocahonka.comfywhitelist.config.message.MessageFormat
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

        val playerNameList = storage.allWhitelistedPlayers

        val messageComponent = if (playerNameList.isEmpty()) {
            MessageConfig.emptyWhitelistedPlayersList
        } else {
            val replacementConfig = MessageFormat.ConfigBuilders.playersReplacementConfigBuilder(playerNameList)
            MessageConfig.whitelistedPlayersList.replaceText(replacementConfig)
        }
        sender.sendMessage(messageComponent)
        return true
    }

}