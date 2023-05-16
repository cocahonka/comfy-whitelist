package com.cocahonka.comfywhitelist.listeners

import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import com.cocahonka.comfywhitelist.storage.Storage
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST

/**
 * This class represents a listener for the PlayerPreLoginEvent.
 * It checks if the player is on the whitelist before they can join the server.
 *
 * @property storage The [Storage] instance used to access the whitelist data.
 */
class PlayerPreLoginEvent(private val storage: Storage) : Listener {

    /**
     * Handles the [AsyncPlayerPreLoginEvent] by checking if the player is whitelisted.
     * If the player is not on the whitelist and the whitelist is enabled, they will be
     * disallowed from joining the server with a [KICK_WHITELIST] result.
     *
     * @param event The [AsyncPlayerPreLoginEvent] instance representing the event being handled.
     */
    @EventHandler
    fun onPlayerPreLoginEvent(event: AsyncPlayerPreLoginEvent) {
        if (!GeneralConfig.whitelistEnabled) {
            return
        }

        val playerName = event.name
        if (!storage.isPlayerWhitelisted(playerName)) {
            event.disallow(
                KICK_WHITELIST,
                MessageConfig.notWhitelisted
            )
        }
    }
}