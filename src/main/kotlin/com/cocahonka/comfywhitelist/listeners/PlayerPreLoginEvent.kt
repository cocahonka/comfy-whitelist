package com.cocahonka.comfywhitelist.listeners

import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import com.cocahonka.comfywhitelist.storage.Storage
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

class PlayerPreLoginEvent(private val storage: Storage) : Listener {

    @EventHandler
    fun onPlayerPreLoginEvent(event: AsyncPlayerPreLoginEvent) {
        if (!GeneralConfig.whitelistEnabled) {
            return
        }

        val playerName = event.name
        if (!storage.isPlayerWhitelisted(playerName)) {
            val message = MessageConfig.notWhitelisted
            event.disallow(
                AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                Component.text(message)
            )
        }
    }
}