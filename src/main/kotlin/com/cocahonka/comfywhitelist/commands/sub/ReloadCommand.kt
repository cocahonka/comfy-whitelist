package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.ComfyWhitelist
import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import net.kyori.adventure.text.Component
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
            val message = MessageConfig.invalidUsage.replace("%s", usage)
            sender.sendMessage(Component.text(message))
            return false
        }

        val message = MessageConfig.pluginReloaded
        sender.sendMessage(Component.text(message))
        plugin.reloadConfigs()
        return true
    }

}