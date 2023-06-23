package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.LegacyUtils.sendMessage
import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import org.bukkit.command.CommandSender

/**
 * Represents the "status" command, which displays plugin whitelist status (on/off).
 */
class StatusCommand : SubCommand {

    override val identifier = "status"
    override val permission = "comfywhitelist.status"
    override val usage = "/comfywl status"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (isInvalidUsage(sender) { args.isEmpty() }) return false

        val message = if (GeneralConfig.whitelistEnabled) {
            MessageConfig.whitelistEnabled
        } else {
            MessageConfig.whitelistDisabled
        }

        sender.sendMessage(message)

        return true
    }

}