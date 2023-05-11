package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.CommandTestBase
import com.cocahonka.comfywhitelist.config.message.Message
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ClearCommandTest : CommandTestBase() {

    @Test
    fun `when console is sender`() {
        val clearCommand = ClearCommand(storage)
        val anotherPlayer = server.addPlayer()

        storage.addPlayer(player.name)
        storage.addPlayer(anotherPlayer.name)

        val result = handler.onCommand(
            sender = console,
            command = command,
            label = clearCommand.identifier,
            args = arrayOf(clearCommand.identifier)
        )

        assertTrue(result)
        assertStorageEmpty()
        assertEquals(console.nextMessage(), Message.WhitelistCleared.getDefault(locale))
        console.assertNoMoreSaid()
    }

    @Test
    fun `when player is sender without permission`() {
        val clearCommand = ClearCommand(storage)
        val anotherPlayer = server.addPlayer()

        storage.addPlayer(player.name)
        storage.addPlayer(anotherPlayer.name)

        val result = handler.onCommand(
            sender = player,
            command = command,
            label = clearCommand.identifier,
            args = arrayOf(clearCommand.identifier)
        )

        assertFalse(result)
        assertWhitelisted(player.name)
        assertWhitelisted(anotherPlayer.name)
        assertEquals(player.nextMessage(), Message.NoPermission.getDefault(locale))
        player.assertNoMoreSaid()
    }

    @Test
    fun `when player is sender with permission`() {
        val clearCommand = ClearCommand(storage)
        val anotherPlayer = server.addPlayer()

        player.addAttachment(plugin, clearCommand.permission, true)
        storage.addPlayer(player.name)
        storage.addPlayer(anotherPlayer.name)

        val result = handler.onCommand(
            sender = player,
            command = command,
            label = clearCommand.identifier,
            args = arrayOf(clearCommand.identifier)
        )

        assertTrue(result)
        assertStorageEmpty()
        assertEquals(player.nextMessage(), Message.WhitelistCleared.getDefault(locale))
        player.assertNoMoreSaid()
    }

    @Test
    fun `when to many arguments`() {
        val clearCommand = ClearCommand(storage)
        val anotherPlayer = server.addPlayer()

        storage.addPlayer(player.name)
        storage.addPlayer(anotherPlayer.name)

        val result = handler.onCommand(
            sender = console,
            command = command,
            label = clearCommand.identifier,
            args = arrayOf(clearCommand.identifier)
        )

        assertFalse(result)
        assertWhitelisted(player.name)
        assertWhitelisted(anotherPlayer.name)
        assertEquals(console.nextMessage(), Message.InvalidUsage.getDefault(locale))
        console.assertNoMoreSaid()
    }

}