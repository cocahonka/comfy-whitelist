package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.LegacyUtils.sendMessage
import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import org.bukkit.command.CommandSender

/**
 * Represents the "on" command, which enables the whitelist feature in the plugin.
 *
 * @property generalConfig The [GeneralConfig] instance to manage plugin configuration.
 */
class EnableCommand(private val generalConfig: GeneralConfig) : SubCommand {

    override val identifier = "on"
    override val permission = "comfywhitelist.on"
    override val usage = "/comfywl on"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if(isInvalidUsage(sender) { args.isEmpty() }) return false

        val message = if (GeneralConfig.whitelistEnabled){
            MessageConfig.whitelistAlreadyEnabled
        } else {
            generalConfig.enableWhitelist()
            MessageConfig.whitelistEnabled
        }
        sender.sendMessage(message)
        return true
    }

}