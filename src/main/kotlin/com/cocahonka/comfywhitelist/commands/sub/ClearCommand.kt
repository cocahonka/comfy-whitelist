package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.SubCommand
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import com.cocahonka.comfywhitelist.storage.Storage
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender

/**
 * Represents the "clear" subcommand, which clears the whitelist of all players.
 *
 * @property storage The [Storage] instance used to interact with the whitelist.
 */
class ClearCommand(private val storage: Storage) : SubCommand {

    override val identifier = "clear"
    override val permission = "comfywhitelist.clear"
    override val usage = "/comfywl clear"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.isNotEmpty()){
            val message = MessageConfig.invalidUsage.replace("%s", usage)
            sender.sendMessage(Component.text(message))
            return false
        }

        val message = MessageConfig.whitelistCleared
        sender.sendMessage(Component.text(message))
        return storage.clear()
    }

}