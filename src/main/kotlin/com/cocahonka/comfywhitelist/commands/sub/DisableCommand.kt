package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import com.cocahonka.comfywhitelist.config.message.MessageTagResolvers
import net.kyori.adventure.text.minimessage.MiniMessage
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
        if(isInvalidUsage(sender) { args.isEmpty() }) return false

        val messageComponent = if (!GeneralConfig.whitelistEnabled){
            val message = MessageConfig.whitelistAlreadyDisabled
            MiniMessage.miniMessage().deserialize(
                message,
                MessageTagResolvers.off,
            )
        } else {
            generalConfig.disableWhitelist()
            val message = MessageConfig.whitelistDisabled
            MiniMessage.miniMessage().deserialize(
                message,
                MessageTagResolvers.off,
            )
        }
        sender.sendMessage(messageComponent)
        return true
    }

}