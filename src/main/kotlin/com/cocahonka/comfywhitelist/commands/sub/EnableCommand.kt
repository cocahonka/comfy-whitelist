package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import net.kyori.adventure.text.Component
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
            val message = MessageConfig.invalidUsage.replace("%s", usage)
            sender.sendMessage(Component.text(message))
            return false
        }

        val message = if (GeneralConfig.whitelistEnabled){
            MessageConfig.whitelistAlreadyEnabled
        } else {
            generalConfig.enableWhitelist()
            MessageConfig.whitelistEnabled
        }
        sender.sendMessage(Component.text(message))
        return true
    }

}