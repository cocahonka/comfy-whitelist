package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import com.cocahonka.comfywhitelist.config.message.MessageTagResolvers
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
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
        if (args.isNotEmpty()) {
            val message = MessageConfig.invalidUsage
            val messageComponent = MiniMessage.miniMessage().deserialize(
                message,
                MessageTagResolvers.warning,
                MessageTagResolvers.insertUsage(usage),
            )
            sender.sendMessage(messageComponent)
            return false
        }

        val messageComponent = if (GeneralConfig.whitelistEnabled){
            val message = MessageConfig.whitelistAlreadyEnabled
            MiniMessage.miniMessage().deserialize(
                message,
                MessageTagResolvers.success,
            )
        } else {
            generalConfig.enableWhitelist()
            val message = MessageConfig.whitelistEnabled
            MiniMessage.miniMessage().deserialize(
                message,
                MessageTagResolvers.success,
            )
        }
        sender.sendMessage(messageComponent)

        return true
    }

}