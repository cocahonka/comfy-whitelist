package com.cocahonka.comfywhitelist.storage

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

abstract class WhitelistStorageTestBase {
    abstract fun createStorage(): WhitelistStorage

    @Test
    fun `add player to whitelist`() {
        val storage = createStorage()
        val playerName = "testPlayer"

        storage.addPlayer(playerName)
        assertTrue(storage.isPlayerWhitelisted(playerName))
    }

    @Test
    fun `remove player from whitelist`() {
        val storage = createStorage()
        val playerName = "testPlayer"
        storage.addPlayer(playerName)

        storage.removePlayer(playerName)
        assertFalse(storage.isPlayerWhitelisted(playerName))
    }

    @Test
    fun `get whitelisted players`() {
        val storage = createStorage()
        val playerNames = listOf("player1", "player2", "player3")

        playerNames.forEach { storage.addPlayer(it) }
        assertEquals(playerNames, storage.getAllWhitelistedPlayers().toList())
    }

    @Test
    fun `clear whitelist`() {
        val storage = createStorage()
        val playerNames = listOf("player1", "player2", "player3")

        playerNames.forEach { storage.addPlayer(it) }
        storage.clear()

        assertEquals(emptyList<String>(), storage.getAllWhitelistedPlayers().toList())
    }

}