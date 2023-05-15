package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.ComfyWhitelist
import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import com.cocahonka.comfywhitelist.config.message.MessageTagResolvers
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender

/**
 * Represents the "reload" command, which reloads the plugin configuration.
 *
 * @property plugin The [ComfyWhitelist] plugin instance.
 */
class ReloadCommand(private val plugin: ComfyWhitelist) : SubCommand {

    override val identifier = "reload"
    override val permission = "comfywhitelist.reload"
    override val usage = "/comfywl reload"

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

        val message = MessageConfig.pluginReloaded
        val messageComponent = MiniMessage.miniMessage().deserialize(
            message,
            MessageTagResolvers.success,
        )
        sender.sendMessage(messageComponent)
        plugin.reloadConfigs()

        return true
    }

}