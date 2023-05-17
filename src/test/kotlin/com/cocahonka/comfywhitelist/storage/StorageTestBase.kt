package com.cocahonka.comfywhitelist.storage

import com.cocahonka.comfywhitelist.api.Storage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

abstract class StorageTestBase {
    abstract fun createStorage(): Storage

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
        assertEquals(playerNames, storage.allWhitelistedPlayers.toList())
    }

    @Test
    fun `clear whitelist`() {
        val storage = createStorage()
        val playerNames = listOf("player1", "player2", "player3")

        playerNames.forEach { storage.addPlayer(it) }
        storage.clear()

        assertEquals(emptyList<String>(), storage.allWhitelistedPlayers.toList())
    }

    @Test
    fun `save and load whitelisted players`() {
        val storage = createStorage()
        val playerNames = listOf("player1", "player2", "player3")

        playerNames.forEach { storage.addPlayer(it) }
        assertTrue(storage.save(), "Failed to save data")

        val storage2 = createStorage()
        assertTrue(storage2.load(), "Failed to load data")
        val loadedPlayers = storage2.allWhitelistedPlayers.toList()

        assertEquals(playerNames.size, loadedPlayers.size)
        assertTrue(loadedPlayers.containsAll(playerNames))
    }

    @Test
    fun `save and load empty whitelist`() {
        val storage = createStorage()
        assertTrue(storage.save(), "Failed to save data")

        val storage2 = createStorage()
        assertTrue(storage2.load(), "Failed to load data")
        assertEquals(emptyList<String>(), storage2.allWhitelistedPlayers.toList())
    }

}