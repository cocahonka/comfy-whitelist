package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import org.bukkit.command.CommandSender

/**
 * Represents the "off" command, which disables the whitelist feature in the plugin.
 *
 * @property generalConfig The [GeneralConfig] instance to manage plugin configuration.
 */
class DisableCommand(private val generalConfig: GeneralConfig) : SubCommand {

    override val identifier = "off"
    override val permission = "comfywhitelist.off"
    override val usage = "/comfywl off"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (isInvalidUsage(sender) { args.isEmpty() }) return false

        val message = if (!GeneralConfig.whitelistEnabled) {
            MessageConfig.whitelistAlreadyDisabled
        } else {
            generalConfig.disableWhitelist()
            MessageConfig.whitelistDisabled
        }
        sender.sendMessage(message)
        return true
    }

}