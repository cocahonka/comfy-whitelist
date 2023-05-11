package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.CommandTestBase
import com.cocahonka.comfywhitelist.config.message.Message
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RemoveCommandTest : CommandTestBase() {
    @Test
    fun `when console is sender`() {
        val removeCommand = RemoveCommand(storage)
        storage.addPlayer(player.name)

        val result = handler.onCommand(
            sender = console,
            command = command,
            label = removeCommand.identifier,
            args = arrayOf(removeCommand.identifier, player.name),
        )

        assertTrue(result)
        assertNotWhitelisted(player.name)
        assertEquals(
            console.nextMessage(),
            Message.PlayerRemoved.getDefault(locale).replace("%s", player.name)
        )
        console.assertNoMoreSaid()
    }

    @Test
    fun `when player is sender without permission`() {
        val removeCommand = RemoveCommand(storage)
        val anotherPlayer = server.addPlayer()
        storage.addPlayer(anotherPlayer.name)
        val result = handler.onCommand(
            sender = player,
            command = command,
            label = removeCommand.identifier,
            args = arrayOf(removeCommand.identifier, anotherPlayer.name),
        )

        assertFalse(result)
        assertWhitelisted(anotherPlayer.name)
        assertEquals(player.nextMessage(), Message.NoPermission.getDefault(locale))
        player.assertNoMoreSaid()
    }

    @Test
    fun `when player is sender with permission`() {
        val removeCommand = RemoveCommand(storage)
        val anotherPlayer = server.addPlayer()
        storage.addPlayer(anotherPlayer.name)
        player.addAttachment(plugin, removeCommand.permission, true)

        val result = handler.onCommand(
            sender = player,
            command = command,
            label = removeCommand.identifier,
            args = arrayOf(removeCommand.identifier, anotherPlayer.name),
        )

        assertTrue(result)
        assertNotWhitelisted(anotherPlayer.name)
        assertEquals(
            player.nextMessage(),
            Message.PlayerRemoved.getDefault(locale).replace("%s", player.name)
        )
        player.assertNoMoreSaid()
    }

    @Test
    fun `when player name not provided`() {
        val removeCommand = RemoveCommand(storage)
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = removeCommand.identifier,
            args = arrayOf(removeCommand.identifier),
        )

        assertFalse(result)
        assertEquals(
            console.nextMessage(),
            Message.InvalidUsage.getDefault(locale).replace("%s", removeCommand.usage)
        )
        console.assertNoMoreSaid()
    }

    @Test
    fun `when to many arguments`() {
        val removeCommand = RemoveCommand(storage)
        storage.addPlayer(player.name)
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = removeCommand.identifier,
            args = arrayOf(removeCommand.identifier, player.name, player.name),
        )

        assertFalse(result)
        assertWhitelisted(player.name)
        assertEquals(
            console.nextMessage(),
            Message.InvalidUsage.getDefault(locale).replace("%s", removeCommand.usage)
        )
        console.assertNoMoreSaid()
    }

}